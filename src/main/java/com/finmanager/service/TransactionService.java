package com.finmanager.service;

import com.finmanager.dao.IDao;
import com.finmanager.dto.TransactionDto;
import com.finmanager.dtoMapper.TransactionDtoMapper;
import com.finmanager.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService implements IService<TransactionDto> {

    private IDao<Transaction> transactionDao;
    private TransactionDtoMapper transactionDtoMapper;

    @Autowired
    public TransactionService(IDao<Transaction> transactionDao, TransactionDtoMapper transactionDtoMapper) {
        this.transactionDao = transactionDao;
        this.transactionDtoMapper = transactionDtoMapper;
    }

    @Override
    public TransactionDto create(TransactionDto transactionDto) {
        return transactionDtoMapper.transactionToTransactionDto(transactionDao.create(transactionDtoMapper.transactionDtoToTransaction(transactionDto)));
    }

    @Override
    public TransactionDto findById(Long id) {
        return transactionDtoMapper.transactionToTransactionDto(transactionDao.findById(id));
    }

    @Override
    public List<TransactionDto> findAll() {
        return transactionDao.findAll().stream().map(transactionDtoMapper::transactionToTransactionDto).collect(Collectors.toList());
    }

    @Override
    public TransactionDto update(TransactionDto transactionDto) {
        return transactionDtoMapper.transactionToTransactionDto(transactionDao.update(transactionDtoMapper.transactionDtoToTransaction(transactionDto)));
    }

    @Override
    public boolean delete(Long id) {
        return transactionDao.delete(id);
    }
}
