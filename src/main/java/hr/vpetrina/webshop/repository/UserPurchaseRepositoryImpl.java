package hr.vpetrina.webshop.repository;

import hr.vpetrina.webshop.model.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserPurchaseRepositoryImpl implements UserPurchaseRepository {

    public static final String SELECT_PURCHASE = "SELECT * FROM USER_PURCHASE WHERE ID = ?";
    public static final String SELECT_ALL_PURCHASES = "SELECT * FROM USER_PURCHASE";
    public static final String SELECT_PURCHASE_BY_USER = "SELECT * FROM USER_PURCHASE WHERE USER_ID = ?";

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertUserPurchase;

    public UserPurchaseRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.insertUserPurchase = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("USER_PURCHASE")
                .usingGeneratedKeyColumns("ID");
    }

    @Override
    public UserPurchase insert(UserPurchase userPurchase) {
        Map<String, Object> parameterMap = new HashMap<>();
        parameterMap.put("QUANTITY", userPurchase.getQuantity());
        parameterMap.put("TOTAL_PRICE", userPurchase.getTotalPrice());
        parameterMap.put("DATE", userPurchase.getDate());
        parameterMap.put("PAYMENT", userPurchase.getPaymentType().name());
        parameterMap.put("GUITAR_ID", userPurchase.getGuitarId());
        parameterMap.put("USER_ID", userPurchase.getUserId());

        Number generatedKey = insertUserPurchase.executeAndReturnKey(parameterMap);
        userPurchase.setId((Integer)generatedKey);

        return userPurchase;
    }

    @Override
    public Optional<UserPurchase> getById(Integer id) {
        UserPurchase purchase =  jdbcTemplate.queryForObject(
                SELECT_PURCHASE,
                new UserPurchaseRepositoryImpl.PurchaseRowMapper(),
                id
        );
        return Optional.ofNullable(purchase);
    }

    @Override
    public List<UserPurchase> getAll() {
        return jdbcTemplate.query(SELECT_ALL_PURCHASES, new PurchaseRowMapper());
    }

    @Override
    public List<UserPurchase> getByUserId(Integer userId) {
        return jdbcTemplate.query(
                SELECT_PURCHASE_BY_USER,
                new UserPurchaseRepositoryImpl.PurchaseRowMapper(),
                userId
        );
    }

    private static class PurchaseRowMapper implements RowMapper<UserPurchase> {

        @Override
        public UserPurchase mapRow(ResultSet rs, int rowNum) throws SQLException {
            UserPurchase purchase = new UserPurchase();

            purchase.setId(rs.getInt("ID"));
            purchase.setDate(rs.getDate("DATE"));
            purchase.setPaymentType(PaymentType.valueOf(rs.getString("PAYMENT")));
            purchase.setQuantity(rs.getInt("QUANTITY"));
            purchase.setTotalPrice(rs.getDouble("TOTAL_PRICE"));
            purchase.setGuitarId(rs.getInt("GUITAR_ID"));
            purchase.setUserId(rs.getInt("USER_ID"));

            return purchase;
        }
    }
}
