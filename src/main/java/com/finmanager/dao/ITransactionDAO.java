package com.finmanager.dao;

import com.finmanager.model.Transaction;

import java.util.List;

public interface ITransactionDAO extends IDao<Transaction> {
    List<Transaction> getTransactionsByOperationId(Long id);
    List<Transaction> getTransactionsByCategoryId(Long id);
    List<Transaction> getTransactionsByUserId(Long id);

}
