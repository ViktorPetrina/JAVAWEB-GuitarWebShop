package hr.vpetrina.webshop.repository;

import hr.vpetrina.webshop.model.GuitarCategory;
import hr.vpetrina.webshop.model.UserLogin;
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
public class UserLoginRepositoryImpl implements UserLoginRepository {

    public static final String SELECT_ALL_ITEMS = "SELECT * FROM USER_LOGIN";
    public static final String SELECT_ITEM = "SELECT * FROM USER_LOGIN WHERE ID = ?";

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertUserLogin;

    public UserLoginRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.insertUserLogin = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("USER_LOGIN")
                .usingGeneratedKeyColumns("ID");
    }

    @Override
    public List<UserLogin> getAll() {
        return jdbcTemplate.query(SELECT_ALL_ITEMS, new UserLoginRowMapper());
    }

    @Override
    public Optional<UserLogin> getById(Integer id) {
        UserLogin login =  jdbcTemplate.queryForObject(
                SELECT_ITEM,
                new UserLoginRowMapper(),
                id
        );
        return Optional.ofNullable(login);
    }

    @Override
    public UserLogin insert(UserLogin userLogin) {
        Map<String, Object> parameterMap = new HashMap<>();
        parameterMap.put("USER_ID", userLogin.getUserId());
        parameterMap.put("DATE", userLogin.getLoginDate());
        parameterMap.put("IP_ADDRESS", userLogin.getIpAddress());

        Number generatedKey = insertUserLogin.executeAndReturnKey(parameterMap);
        userLogin.setId((Integer)generatedKey);

        return userLogin;
    }

    private static class UserLoginRowMapper implements RowMapper<UserLogin> {

        @Override
        public UserLogin mapRow(ResultSet rs, int rowNum) throws SQLException {
            UserLogin login = new UserLogin();
            login.setId(rs.getInt("ID"));
            login.setUserId(rs.getInt("USER_ID"));
            login.setLoginDate(rs.getDate("DATE"));
            login.setIpAddress(rs.getString("IP_ADDRESS"));

            return login;
        }
    }
}
