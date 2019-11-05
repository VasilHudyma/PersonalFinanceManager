package com.finmanager.service;

import com.finmanager.dto.UserDto;

import java.util.List;

public interface IUserService extends IService<UserDto> {
    UserDto findUserByEmail(String email);
}
