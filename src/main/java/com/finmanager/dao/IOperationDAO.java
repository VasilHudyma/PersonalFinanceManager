package com.finmanager.dao;

import com.finmanager.model.Operation;

import java.util.List;


interface IOperationDAO {
    Operation create(Operation operation);
    Operation update(Operation operation);
    boolean delete(Long id);
    Operation findById(Long id);
    List<Operation> findAll();

}
