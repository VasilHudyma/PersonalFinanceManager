package com.finmanager.dao;

import com.finmanager.exceptions.DbOperationException;
import com.finmanager.exceptions.NotFoundException;
import com.finmanager.mapper.TransactionMapper;
import com.finmanager.model.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class TransactionDAOImpl implements IDao<Transaction> {

    private JdbcTemplate jdbcTemplate;
    private TransactionMapper transactionMapper;
    private static final Logger logger = LoggerFactory.getLogger(TransactionDAOImpl.class);

    @Autowired
    public TransactionDAOImpl(DataSource dataSource, TransactionMapper transactionMapper) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.transactionMapper = transactionMapper;
    }

    @Override
    public Transaction create(Transaction transaction) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        LocalDateTime dateTime = LocalDateTime.now();
        transaction.setCreatedDate(dateTime);
        transaction.setUpdatedDate(dateTime);

        try {
            jdbcTemplate.update(connection -> createStatement(transaction, connection), keyHolder);
            transaction.setId((Long) Optional.ofNullable(Objects.requireNonNull(keyHolder.getKeys()).get("id"))
                    .orElseThrow(() -> new DbOperationException("Create Transaction Dao method error: AutoGeneratedKey = null")));
            return transaction;
        } catch (Exception e) {
            logger.error("Creating transaction: {} error: ", transaction, e);
            throw new DbOperationException("Can't create transaction: " + transaction + " Error: " + e.getMessage());
        }
    }

    @Override
    public Transaction findById(Long id) {
        try {
            return jdbcTemplate.queryForObject(Queries.SQL_TRANSACTION_FIND_BY_ID, transactionMapper, id);
        } catch (EmptyResultDataAccessException | NotFoundException e) {
            throw getAndLogTransactionNotFoundException(id);
        } catch (Exception e) {
            logger.error("Can't get transaction with id = {} Error: ", id, e);
            throw new DbOperationException("Can't get transaction with id = " + id + " Error: " + e.getMessage());
        }
    }

    @Override
    public List<Transaction> findAll() {
        try {
            return jdbcTemplate.query(Queries.SQL_TRANSACTION_FIND_ALL, transactionMapper);
        } catch (Exception e) {
            logger.error("Can't get all transactions. Error:", e);
            throw new DbOperationException("Can't get all transactions. Error:" + e.getMessage());
        }
    }

    @Override
    public Transaction update(Transaction transaction) {
        int rowsAffected;
        transaction.setUpdatedDate(LocalDateTime.now());
        try {
            rowsAffected = jdbcTemplate.update(Queries.SQL_TRANSACTION_UPDATE,
                    transaction.getCategoryId(),
                    transaction.getOperationId(),
                    transaction.getSum(),
                    transaction.getDescription(),
                    transaction.getUpdatedDate(),
                    transaction.getId());
        } catch (Exception e) {
            logger.error("Updating transaction: {} error: ", transaction, e);
            throw new DbOperationException("Updating transaction: " + transaction + " error: " + e.getMessage());
        }
        if (rowsAffected < 1) {
            throw getAndLogTransactionNotFoundException(transaction.getId());
        } else
            return transaction;
    }

    @Override
    public boolean delete(Long id) {
        int rowsAffected;
        try {
            rowsAffected = jdbcTemplate.update(Queries.SQL_TRANSACTION_DELETE, id);
        } catch (Exception e) {
            logger.error("Deleting transaction with id = {} error: ", id, e);
            throw new DbOperationException("Deleting transaction with id = " + id + " error: " + e.getMessage());
        }
        if (rowsAffected < 1) {
            throw getAndLogTransactionNotFoundException(id);
        } else
            return true;
    }

    private PreparedStatement createStatement(Transaction transaction, Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(Queries.SQL_TRANSACTION_CREATE, Statement.RETURN_GENERATED_KEYS);
        ps.setLong(1, transaction.getCategoryId());
        ps.setLong(2, transaction.getOperationId());
        ps.setDouble(3, transaction.getSum());
        ps.setString(4, transaction.getDescription());
        ps.setObject(5, transaction.getCreatedDate());
        ps.setObject(6, transaction.getUpdatedDate());

        return ps;
    }

    private NotFoundException getAndLogTransactionNotFoundException(Long id) {
        NotFoundException notFoundException = new NotFoundException("Transaction not found");
        logger.error("Runtime exception. Transaction not found id = {}. Message: {}", id, notFoundException.getMessage());
        return notFoundException;
    }

    private class Queries {
        static final String SQL_TRANSACTION_CREATE = "INSERT INTO transactions" +
                "(category_id, operation_id, sum, description, created_date, updated_date) values (?,?,?,?,?,?)";
        static final String SQL_TRANSACTION_DELETE = "DELETE FROM transactions WHERE id=?";
        static final String SQL_TRANSACTION_FIND_ALL = "SELECT * FROM transactions";
        static final String SQL_TRANSACTION_FIND_BY_ID = "SELECT * FROM transactions WHERE id=?";
        static final String SQL_TRANSACTION_UPDATE = "UPDATE transactions set category_id = ?, operation_id=?, sum = ?, description=?, updated_date = ? where id = ?";
    }
}
