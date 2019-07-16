package com.finmanager.service;

import java.util.List;

public interface IService<T> {
    T create(T t);

    T findById(Long id);

    List<T> findAll();

    T update(T t);

    boolean delete(Long id);
}
