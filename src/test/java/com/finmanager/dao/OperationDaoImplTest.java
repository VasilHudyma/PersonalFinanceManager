package com.finmanager.dao;

import com.finmanager.exceptions.DbOperationException;
import com.finmanager.exceptions.NotFoundException;
import com.finmanager.model.Operation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OperationDaoImplTest extends BaseTest {

    @Autowired
    private OperationDAOImpl operationDAO;


    private Operation operation = new Operation(Long.MAX_VALUE, "Vytrata", LocalDateTime.now(), LocalDateTime.now());

    @Test
    void deleteOperation() {
        assertTrue(operationDAO.delete(operation.getId()));
    }

    @Test
    void deleteOperation_invalidId() {
        assertThrows(NotFoundException.class, () -> operationDAO.delete(Long.MAX_VALUE));
    }

    @Test
    void createOperation() {
        assertEquals(operation, operationDAO.create(operation));
    }

    @Test
    void createOperation_nullValue() {
        operation.setName(null);
        assertThrows(DbOperationException.class, () -> operationDAO.create(operation));
    }

    @Test
    void findOperation() {
        assertEquals(operation, operationDAO.findById(operation.getId()));
    }

    @Test
    void findOperation_invalidId() {
        assertThrows(NotFoundException.class, () -> operationDAO.findById(Long.MAX_VALUE));
    }

    @Test
    void findAllOperations() {

        clearAllTables();

        List<Operation> operations = Arrays.asList(operationDAO.create(operation), operationDAO.create(new Operation(2L, "sec", LocalDateTime.now(), LocalDateTime.now())),
                operationDAO.create(new Operation(3L, "third", LocalDateTime.now(), LocalDateTime.now())));

        List<Operation> operationsFromDb = operationDAO.findAll();
        for (int i = 0; i < operations.size(); i++) {
            assertEquals(operations.get(i), operationsFromDb.get(i));
        }
    }

    @Test
    void updateOperation() {
        operation.setName("updated");
        operationDAO.update(operation);
        assertEquals(operation, operationDAO.findById(operation.getId()));
    }

    @Test
    void updateOperation_operationNotExist() {
        assertThrows(NotFoundException.class, () -> operationDAO.update(new Operation(Long.MAX_VALUE, "dfd", LocalDateTime.now(), LocalDateTime.now())));
    }

    @Test
    void updateOperation_nullValue() {
        operation.setName(null);
        assertThrows(DbOperationException.class, () -> operationDAO.update(operation));
    }

    @AfterEach
    void cleanOperations() {
        clearAllTables();
    }

    @BeforeEach
    void createTestOperation() {
        operation = operationDAO.create(operation);
    }
}
