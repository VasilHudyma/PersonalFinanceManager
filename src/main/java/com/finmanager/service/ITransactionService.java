package com.finmanager.service;

import com.finmanager.dto.TransactionDto;

import java.util.List;

public interface ITransactionService extends IService<TransactionDto> {
    List<TransactionDto> getTransactionByOperationId(Long id);

    List<TransactionDto> getTransactionByCategoryId(Long id);

    List<TransactionDto> getTransactionByUserId(Long id);
}
