package com.finmanager.mapper;

import com.finmanager.model.Operation;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

@Component
public class OperationMapper implements RowMapper<Operation> {

    @Override
    public Operation mapRow(ResultSet resultSet, int i) throws SQLException {
        Operation operation = new Operation();
        operation.setId(resultSet.getLong("id"));
        operation.setName(resultSet.getString("name"));
        operation.setCreatedDate(resultSet.getObject("created_date", LocalDateTime.class));
        operation.setUpdatedDate(resultSet.getObject("updated_date", LocalDateTime.class));
        return operation;

    }
}
