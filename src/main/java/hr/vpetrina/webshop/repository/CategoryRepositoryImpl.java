package hr.vpetrina.webshop.repository;

import hr.vpetrina.webshop.model.GuitarCategory;
import hr.vpetrina.webshop.model.GuitarItem;
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
public class CategoryRepositoryImpl implements CategoryRepository {

    public static final String SELECT_ALL_ITEMS = "SELECT * FROM GUITAR_CATEGORY";
    public static final String SELECT_ITEM = "SELECT * FROM GUITAR_CATEGORY WHERE ID = ?";
    public static final String DELETE_ITEM = "DELETE FROM GUITAR_CATEGORY WHERE ID = ?";

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertGuitarItem;

    public CategoryRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.insertGuitarItem = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("GUITAR_CATEGORY")
                .usingGeneratedKeyColumns("ID");
    }

    @Override
    public List<GuitarCategory> getAll() {
        return jdbcTemplate.query(SELECT_ALL_ITEMS, new CategoryRowMapper());
    }

    @Override
    public Optional<GuitarCategory> getById(Integer id) {
        GuitarCategory category =  jdbcTemplate.queryForObject(
                SELECT_ITEM,
                new CategoryRowMapper(),
                id
        );
        return Optional.ofNullable(category);
    }

    @Override
    public GuitarCategory insert(GuitarCategory item) {
        Map<String, Object> parameterMap = new HashMap<>();
        parameterMap.put("NAME", item.getName());

        Number generatedKey = insertGuitarItem.executeAndReturnKey(parameterMap);
        item.setId((Integer)generatedKey);

        return item;
    }

    @Override
    public Optional<GuitarCategory> update(Integer id, GuitarCategory item) {
        return Optional.empty();
    }

    @Override
    public void delete(Integer id) {
        jdbcTemplate.update(DELETE_ITEM, id);
    }

    private static class CategoryRowMapper implements RowMapper<GuitarCategory> {

        @Override
        public GuitarCategory mapRow(ResultSet rs, int rowNum) throws SQLException {
            GuitarCategory category = new GuitarCategory();
            category.setId(rs.getInt("ID"));
            category.setName(rs.getString("NAME"));
            return category;
        }
    }
}
