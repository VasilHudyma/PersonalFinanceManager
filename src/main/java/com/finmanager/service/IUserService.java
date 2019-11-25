package com.finmanager.service;

import com.finmanager.dto.UserDto;

public interface IUserService extends IService<UserDto> {
    UserDto findUserByEmail(String email);

    boolean updatePassword(String email, String newPassword);
}
