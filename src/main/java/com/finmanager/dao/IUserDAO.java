package com.finmanager.dao;

import com.finmanager.model.User;

public interface IUserDAO extends IDao<User> {
    User findByEmail(String email);
}
