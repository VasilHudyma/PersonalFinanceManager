package com.finmanager.service;

import com.finmanager.dao.ITransactionDAO;
import com.finmanager.dto.TransactionDto;
import com.finmanager.dtoMapper.TransactionDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService implements ITransactionService {

    private ITransactionDAO transactionDao;
    private TransactionDtoMapper transactionDtoMapper;

    @Autowired
    public TransactionService(ITransactionDAO transactionDao, TransactionDtoMapper transactionDtoMapper) {
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

    @Override
    public List<TransactionDto> getTransactionByOperationId(Long id) {
        return transactionDao.findTransactionsByOperationId(id).stream().map(transactionDtoMapper::transactionToTransactionDto).collect(Collectors.toList());
    }

    @Override
    public List<TransactionDto> getTransactionByCategoryId(Long id) {
        return transactionDao.findTransactionsByCategoryId(id).stream().map(transactionDtoMapper::transactionToTransactionDto).collect(Collectors.toList());

    }

    @Override
    public List<TransactionDto> getTransactionByUserId(Long id) {
        return transactionDao.findTransactionsByUserId(id).stream().map(transactionDtoMapper::transactionToTransactionDto).collect(Collectors.toList());

    }
}
