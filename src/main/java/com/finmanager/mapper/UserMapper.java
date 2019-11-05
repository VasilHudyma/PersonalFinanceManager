package com.finmanager.mapper;

import com.finmanager.model.Role;
import com.finmanager.model.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

@Component
public class UserMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        return new User(resultSet.getLong("id"),resultSet.getString("email"),
                resultSet.getString("password"),
                resultSet.getString("name"),
                resultSet.getString("surname"),
                resultSet.getString("phone"),
                Role.valueOf(resultSet.getString("role")),
                resultSet.getObject("created_date", LocalDateTime.class),
                resultSet.getObject("updated_date", LocalDateTime.class));
    }
}
