package com.finmanager.dao;

import com.finmanager.exceptions.DbOperationException;
import com.finmanager.exceptions.NotFoundException;
import com.finmanager.model.Transaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TransactionDaoImplTest extends BaseTest {

    @Autowired
    private IDao<Transaction> transactionDao;

    private Transaction transaction = new Transaction(Long.MAX_VALUE, 1L, 1L, 1L, 14.88, "some description", LocalDateTime.now(), LocalDateTime.now());

    @BeforeEach
    void createTestTransaction() {
        transaction = transactionDao.create(transaction);
    }

    @Test
    void createTransaction() {
        assertEquals(transaction, transactionDao.create(transaction));
    }

    @Test
    void createTransaction_notExistingForeignKeys() {
        transaction.setOperationId(Long.MAX_VALUE);
        transaction.setCategoryId(Long.MAX_VALUE);
        assertThrows(DbOperationException.class, () -> transactionDao.create(transaction));
    }

    @Test
    void createTransaction_nullValue() {
        transaction.setSum(null);
        assertThrows(DbOperationException.class, () -> transactionDao.create(transaction));
    }

    @Test
    void findByIdTransaction() {
        assertEquals(transaction, transactionDao.findById(transaction.getId()));
    }

    @Test
    void findByIdTransaction_invalidId() {
        assertThrows(NotFoundException.class, () -> transactionDao.findById(Long.MAX_VALUE));
    }

    @Test
    void findAllTransactions() {
        cleanTransactions();
        List<Transaction> transactions = Arrays.asList(transactionDao.create(transaction),
                transactionDao.create(new Transaction(2L, 1L, 1L, 1L, 24.88, "second description", LocalDateTime.now(), LocalDateTime.now())),
                transactionDao.create(new Transaction(3L, 1L, 1L, 1L, 34.88, "third description", LocalDateTime.now(), LocalDateTime.now())));
        List<Transaction> transactionsFromDb = transactionDao.findAll();

        for (int i = 0; i < transactions.size(); i++) {
            assertEquals(transactions.get(i), transactionsFromDb.get(i));
        }
    }

    @Test
    void updateTransaction() {
        transaction.setSum(13.99);
        transactionDao.update(transaction);
        assertEquals(transaction, transactionDao.findById(transaction.getId()));
    }

    @Test
    void updateTransaction_invalidId() {
        transaction.setId(Long.MAX_VALUE);
        assertThrows(NotFoundException.class, () -> transactionDao.update(transaction));
    }

    @Test
    void updateTransaction_nullValue() {
        transaction.setSum(null);
        assertThrows(DbOperationException.class, () -> transactionDao.update(transaction));
    }

    @Test
    void deleteTransaction() {
        assertTrue(transactionDao.delete(transaction.getId()));
    }

    @Test
    void deleteTransaction_invalidId() {
        assertThrows(NotFoundException.class, () -> transactionDao.delete(Long.MAX_VALUE));
    }

    @AfterEach
    void cleanTransactions() {
        jdbcTemplate.update("TRUNCATE TABLE transactions RESTART IDENTITY");
    }
}
