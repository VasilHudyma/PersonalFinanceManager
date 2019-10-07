package com.finmanager.dtoMapper;

import com.finmanager.dto.TransactionDto;
import com.finmanager.model.Transaction;
import org.springframework.stereotype.Component;

@Component
public class TransactionDtoMapper {

    public TransactionDto transactionToTransactionDto(Transaction transaction) {
        return new TransactionDto(transaction.getId(), transaction.getCategoryId(), transaction.getOperationId(),transaction.getUserId(),
                transaction.getSum(), transaction.getDescription(), transaction.getCreatedDate(), transaction.getUpdatedDate());
    }

    public Transaction transactionDtoToTransaction(TransactionDto transactionDto) {
        return new Transaction(transactionDto.getId(), transactionDto.getCategoryId(), transactionDto.getOperationId(),
                transactionDto.getUserId(), transactionDto.getSum(), transactionDto.getDescription(), transactionDto.getCreatedDate(), transactionDto.getUpdatedDate());
    }
}
