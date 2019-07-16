package com.finmanager.service;

import com.finmanager.dao.IDao;
import com.finmanager.dto.TransactionDto;
import com.finmanager.dtoMapper.TransactionDtoMapper;
import com.finmanager.model.Transaction;
import name.falgout.jeffrey.testing.junit.mockito.MockitoExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    private final TransactionDto transactionDto = new TransactionDto(1L, 1L, 1L, 100.1, "transDescrpt", LocalDateTime.now(), LocalDateTime.now());
    private TransactionDtoMapper transactionDtoMapper = new TransactionDtoMapper();

    @Mock
    private IDao<Transaction> transactionDao;

    @InjectMocks
    private TransactionService transactionService;

    private Transaction transaction;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);

        transaction = transactionDtoMapper.transactionDtoToTransaction(transactionDto);
        transactionService = new TransactionService(transactionDao, transactionDtoMapper);
    }

    @Test
    void create() {
        doReturn(transaction).when(transactionDao).create(transaction);
        TransactionDto result = transactionService.create(transactionDto);
        assertEquals(transactionDtoMapper.transactionToTransactionDto(transaction), result);
    }

    @Test
    void findById() {
        doReturn(transaction).when(transactionDao).findById(transaction.getId());
        TransactionDto result = transactionService.findById(transactionDto.getId());
        assertEquals(transactionDtoMapper.transactionToTransactionDto(transaction), result);
    }

    @Test
    void findAll() {
        List<Transaction> transactions = Arrays.asList(transaction, new Transaction(12L, 2L, 1L, 100.1, "transDescrpt", LocalDateTime.now(), LocalDateTime.now()));
        doReturn(transactions).when(transactionDao).findAll();

        List<TransactionDto> transactionDtos = transactionService.findAll();
        for (int i = 0; i < transactions.size(); i++) {
            assertEquals(transactionDtoMapper.transactionToTransactionDto(transactions.get(i)), transactionDtos.get(i));
        }
    }

    @Test
    void update() {
        doReturn(transaction).when(transactionDao).update(transaction);
        TransactionDto result = transactionService.update(transactionDto);
        assertEquals(transactionDtoMapper.transactionToTransactionDto(transaction), result);
    }

    @Test
    void delete() {
        doReturn(true).when(transactionDao).delete(transaction.getId());
        assertTrue(transactionService.delete(transaction.getId()));
    }
}
