package com.finmanager.dtoMapper;

import com.finmanager.config.ProfileConfig;
import com.finmanager.dto.TransactionDto;
import com.finmanager.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {ProfileConfig.class})
class TransactionDtoMapperTest {
    private Transaction transaction;
    private TransactionDto transactionDto;

    @Autowired
    private TransactionDtoMapper transactionDtoMapper;

    @BeforeEach
    void init() {
        transaction = new Transaction(1L, 1L, 1L,1L, 100.1, "transDescrpt", LocalDateTime.now(), LocalDateTime.now());
        transactionDto = new TransactionDto();
        transactionDto.setId(transaction.getId());
        transactionDto.setCategoryId(transaction.getCategoryId());
        transactionDto.setOperationId(transaction.getOperationId());
        transactionDto.setUserId(transaction.getUserId());
        transactionDto.setSum(transaction.getSum());
        transactionDto.setDescription(transaction.getDescription());
        transactionDto.setCreatedDate(transaction.getCreatedDate());
        transactionDto.setUpdatedDate(transaction.getUpdatedDate());
    }

    @Test
    void transactionToTransactionDtoTest() {
        assertEquals(transactionDto, transactionDtoMapper.transactionToTransactionDto(transaction));
    }

    @Test
    void transactionDtoToTransactionTest() {
        assertEquals(transaction, transactionDtoMapper.transactionDtoToTransaction(transactionDto));
    }
}
