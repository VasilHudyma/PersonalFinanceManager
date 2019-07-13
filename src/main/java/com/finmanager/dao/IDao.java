package com.finmanager.dao;

import java.util.List;

public interface IDao<T> {
    T create(T t);

    T findById(Long id);

    List<T> findAll();

    T update(T t);

    boolean delete(Long id);
}
