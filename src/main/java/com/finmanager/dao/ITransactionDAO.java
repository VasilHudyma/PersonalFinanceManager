package com.finmanager.dao;

import com.finmanager.model.Transaction;

import java.util.List;

public interface ITransactionDAO extends IDao<Transaction> {
    List<Transaction> findTransactionsByOperationId(Long id);

    List<Transaction> findTransactionsByCategoryId(Long id);

    List<Transaction> findTransactionsByUserId(Long id);

}
