package com.finmanager.mapper;

import com.finmanager.model.Transaction;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;

@Component
public class TransactionMapper implements RowMapper<Transaction> {
    @Override
    public Transaction mapRow(ResultSet resultSet, int i) throws SQLException {
        DecimalFormat df = new DecimalFormat(".##");
        Transaction transaction = new Transaction();
        transaction.setId(resultSet.getLong("id"));
        transaction.setCategoryId(resultSet.getLong("category_id"));
        transaction.setOperationId(resultSet.getLong("operation_id"));
        transaction.setSum(Double.valueOf(df.format(resultSet.getDouble("sum")).replace(",", ".")));
        transaction.setDescription(resultSet.getString("description"));
        transaction.setCreatedDate(resultSet.getObject("created_date", LocalDateTime.class));
        transaction.setUpdatedDate(resultSet.getObject("updated_date", LocalDateTime.class));
        transaction.setUserId(resultSet.getLong("user_id"));
        return transaction;
    }
}
