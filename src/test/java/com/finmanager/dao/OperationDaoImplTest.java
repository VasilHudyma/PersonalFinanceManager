package com.finmanager.dao;

import com.finmanager.mapper.OperationMapper;
import com.finmanager.model.Operation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OperationDaoImplTest extends BaseTest {

    @Autowired
    private OperationDAOImpl operationDAO;


    private Operation operation = new Operation(1L,"Vytrata", LocalDateTime.now(),LocalDateTime.now());

    @Test
    public void delete_test(){
        assertTrue(operationDAO.delete(1L));
    }
}
