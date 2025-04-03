package hr.vpetrina.webshop.repository;

import hr.vpetrina.webshop.model.User;
import hr.vpetrina.webshop.model.UserRole;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private static final String SELECT_BY_EMAIL = "SELECT * FROM USERS WHERE EMAIL = ?";
    private static final String SELECT_BY_ID = "SELECT * FROM USERS WHERE ID = ?";
    private static final String SELECT_BY_USERNAME = "SELECT * FROM USERS WHERE USERNAME = ?";
    private static final String DELETE_USER = "DELETE FROM USERS WHERE ID = ?";

    private static final String PASSWORD_COLUMN = "PASSWORD";
    private static final String USERNAME_COLUMN = "USERNAME";
    private static final String EMAIL_COLUMN = "EMAIL";
    private static final String ROLE_COLUMN = "ROLE";

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertUser;

    public UserRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        insertUser = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("USERS")
                .usingGeneratedKeyColumns("ID")
                .usingColumns(USERNAME_COLUMN, PASSWORD_COLUMN, EMAIL_COLUMN, ROLE_COLUMN);
    }

    @Override
    public Optional<User> findById(Integer id) {
        User user =  jdbcTemplate.queryForObject(
                SELECT_BY_ID,
                new UserRepositoryImpl.UserRowMapper(),
                id
        );
        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        User user =  jdbcTemplate.queryForObject(
                SELECT_BY_EMAIL,
                new UserRepositoryImpl.UserRowMapper(),
                email
        );
        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        try {
            User user =  jdbcTemplate.queryForObject(
                    SELECT_BY_USERNAME,
                    new UserRepositoryImpl.UserRowMapper(),
                    username
            );
            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public User insert(User user) {
        Map<String, Object> parameterMap = new HashMap<>();
        parameterMap.put(USERNAME_COLUMN, user.getUsername());
        parameterMap.put(PASSWORD_COLUMN, user.getPassword());
        parameterMap.put(EMAIL_COLUMN, user.getEmail());
        parameterMap.put(ROLE_COLUMN, user.getRole().name());

        Number generatedKey = insertUser.executeAndReturnKey(parameterMap);
        user.setId((Integer)generatedKey);

        return user;
    }

    @Override
    public Optional<User> update(Integer id, User user) {
        return Optional.empty();
    }

    @Override
    public void delete(Integer id) {
        jdbcTemplate.update(DELETE_USER, id);
    }

    private static class UserRowMapper implements RowMapper<User> {

        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();

            user.setId(rs.getInt("ID"));
            user.setEmail(rs.getString(EMAIL_COLUMN));
            user.setUsername(rs.getString(USERNAME_COLUMN));
            user.setPassword(rs.getString(PASSWORD_COLUMN));
            user.setRole(UserRole.valueOf(rs.getString(ROLE_COLUMN)));

            return user;
        }
    }
}
