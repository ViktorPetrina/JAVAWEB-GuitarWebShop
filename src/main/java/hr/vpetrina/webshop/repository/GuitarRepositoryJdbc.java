package hr.vpetrina.webshop.repository;

import hr.vpetrina.webshop.model.GuitarCategory;
import hr.vpetrina.webshop.model.GuitarItem;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Primary
@Repository
public class GuitarRepositoryJdbc implements GuitarRepository {

    public static final String SELECT_ALL_ITEMS = "SELECT * FROM GUITAR_ITEM";
    public static final String SELECT_ITEM = "SELECT * FROM GUITAR_ITEM WHERE ID = ?";
    public static final String INSERT_ITEM =
            "INSERT INTO GUITAR_ITEM(NAME, DESCRIPTION, QUANTITY, INVENTORY_CATEGORY_ID) VALUES(?, ?, ?, ?)";
    public static final String UPDATE_ITEM =
            "UPDATE INVENTORY_ITEM SET NAME = ?, DESCRIPTION = ?, QUANTITY = ?, INVENTORY_CATEGORY_ID = ? WHERE ID = ?";
    public static final String DELETE_ITEM = "DELETE FROM GUITAR_ITEM WHERE ID = ?";

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert insertGuitarItem;

    public GuitarRepositoryJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.insertGuitarItem = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("GUITAR_ITEM")
                .usingGeneratedKeyColumns("ID");
    }

    @Override
    public List<GuitarItem> getAll() {
        return jdbcTemplate.query(SELECT_ALL_ITEMS, new GuitarRowMapper());
    }

    @Override
    public Optional<GuitarItem> getById(Integer id) {
        GuitarItem guitarItem =  jdbcTemplate.queryForObject(
                SELECT_ITEM,
                new GuitarRowMapper(),
                id
        );
        return Optional.ofNullable(guitarItem);
    }

    @Override
    public GuitarItem insert(GuitarItem item) {
        Map<String, Object> parameterMap = new HashMap<>();
        parameterMap.put("TITLE", item.getTitle());
        parameterMap.put("DESCRIPTION", item.getDescription());
        parameterMap.put("PRICE", item.getPrice());
        parameterMap.put("IMAGE", item.getImageUrl());
        parameterMap.put("CATEGORY", item.getCategory());
        parameterMap.put("PICKUPS", item.getPickups());
        parameterMap.put("NECK", item.getNeck());
        parameterMap.put("BODY", item.getBody());

        jdbcTemplate.update(INSERT_ITEM, parameterMap);

        Number generatedKey = insertGuitarItem.executeAndReturnKey(parameterMap);
        item.setId((Integer)generatedKey);

        return item;
    }

    @Override
    public Optional<GuitarItem> update(Integer id, GuitarItem item) {
        if(getById(id).isEmpty()) {
            return Optional.empty();
        }
        else {
            jdbcTemplate.update( // provjeri da pasu parametri uz konstantu
                    UPDATE_ITEM,
                    item.getTitle(),
                    item.getDescription(),
                    item.getPrice(),
                    item.getBody(),
                    item.getNeck(),
                    item.getPickups(),
                    item.getCategory(),
                    id);
            return getById(id);
        }
    }

    @Override
    public void delete(Integer id) {
        jdbcTemplate.update(DELETE_ITEM, id);
    }

    @Override
    public List<GuitarItem> filterByName(String query) {
        return List.of();
    }

    @Override
    public List<GuitarItem> getFilteredGuitars(GuitarCategory category, String query) {
        StringBuilder sql = new StringBuilder("SELECT * FROM GUITAR_ITEM WHERE 1=1");

        List<Object> params = new ArrayList<>();

        if (category != null) {
            sql.append(" AND CATEGORY = ?");
            params.add(category.name());
        }

        if (query != null && !query.isEmpty()) {
            sql.append(" AND (TITLE ILIKE ? OR DESCRIPTION ILIKE ?)");
            String queryParam = "%" + query + "%";
            params.add(queryParam);
            params.add(queryParam);
        }

        return jdbcTemplate.query(sql.toString(), new GuitarRowMapper(), params.toArray());
    }


    private static class GuitarRowMapper implements RowMapper<GuitarItem> {

        @Override
        public GuitarItem mapRow(ResultSet rs, int rowNum) throws SQLException {
            GuitarItem item = new GuitarItem();

            item.setId(rs.getInt("ID"));
            item.setTitle(rs.getString("TITLE"));
            item.setDescription(rs.getString("DESCRIPTION"));
            item.setPrice(rs.getDouble("PRICE"));
            item.setCategory(GuitarCategory.valueOf(rs.getString("CATEGORY")));
            item.setImageUrl(rs.getString("IMAGE_URL"));
            item.setBody(rs.getString("BODY"));
            item.setNeck(rs.getString("NECK"));
            item.setPickups(rs.getString("PICKUPS"));

            return item;
        }
    }
}
