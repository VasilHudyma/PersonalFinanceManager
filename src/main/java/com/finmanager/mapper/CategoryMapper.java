package com.finmanager.mapper;

import com.finmanager.model.Category;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

@Component
public class CategoryMapper implements RowMapper<Category> {
    @Override
    public Category mapRow(ResultSet resultSet, int i) throws SQLException {
        Category category = new Category();
        category.setId(resultSet.getLong("id"));
        category.setName(resultSet.getString("name"));
        category.setDescription(resultSet.getString("description"));
        category.setCreatedDate(resultSet.getObject("created_date", LocalDateTime.class));
        category.setUpdatedDate(resultSet.getObject("updated_date", LocalDateTime.class));
        return category;
    }
}
