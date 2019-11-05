package com.finmanager.service;

import com.finmanager.dao.IUserDAO;
import com.finmanager.dto.UserDto;
import com.finmanager.dtoMapper.UserDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {

    private IUserDAO userDAO;

    private UserDtoMapper userDtoMapper;

    @Autowired
    public UserService(IUserDAO userDAO, UserDtoMapper dtoMapper) {
        this.userDAO = userDAO;
        this.userDtoMapper = dtoMapper;
    }

    @Override
    public UserDto findUserByEmail(String email) {
        return userDtoMapper.userToUserDto(userDAO.findByEmail(email));
    }

    @Override
    public UserDto create(UserDto userDto) {
        return userDtoMapper.userToUserDto(userDAO.create(userDtoMapper.userDtoToUser(userDto)));
    }

    @Override
    public UserDto findById(Long id) {
        return userDtoMapper.userToUserDto(userDAO.findById(id));
    }

    @Override
    public List<UserDto> findAll() {
        return userDAO.findAll().stream().map(userDtoMapper::userToUserDto).collect(Collectors.toList());
    }

    @Override
    public UserDto update(UserDto userDto) {
        return userDtoMapper.userToUserDto(userDAO.update(userDtoMapper.userDtoToUser(userDto)));
    }

    @Override
    public boolean delete(Long id) {
        return userDAO.delete(id);
    }
}
