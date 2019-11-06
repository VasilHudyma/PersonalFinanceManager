package com.finmanager.service;

import com.finmanager.dao.IUserDAO;
import com.finmanager.dto.UserDto;
import com.finmanager.dtoMapper.UserDtoMapper;
import com.finmanager.model.Role;
import com.finmanager.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;

class UserServiceTest {
    private final UserDto userDto = new UserDto(4L, "mail@mail.com", "Password",
            "name", "surname", "9087238", Role.ADMIN, LocalDateTime.now(), LocalDateTime.now());
    private User user;
    private UserDtoMapper userDtoMapper = new UserDtoMapper();

    @Mock
    private IUserDAO userDao;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
        user = userDtoMapper.userDtoToUser(userDto);
        userService = new UserService(userDao, userDtoMapper);
    }

    @Test
    void createUserTest() {
        doReturn(user).when(userDao).create(user);
        UserDto result = userService.create(userDto);
        assertEquals(userDtoMapper.userToUserDto(user), result);
    }

    @Test
    void findUserById() {
        doReturn(user).when(userDao).findById(user.getId());
        UserDto result = userService.findById(userDto.getId());
        assertEquals(userDtoMapper.userToUserDto(user), result);
    }

    @Test
    void findUserByEmail() {
        doReturn(user).when(userDao).findByEmail(user.getEmail());
        UserDto result = userService.findUserByEmail(userDto.getEmail());
        assertEquals(userDtoMapper.userToUserDto(user), result);
    }

    @Test
    void getAllUsers() {
        List<User> users = Arrays.asList(user,
                new User(5L, "5mail@mail.com", "Password",
                        "name", "surname", "9087238", Role.ADMIN, LocalDateTime.now(), LocalDateTime.now()),
                new User(6L, "6mail@mail.com", "Password",
                        "name", "surname", "9087238", Role.ADMIN, LocalDateTime.now(), LocalDateTime.now()));
        doReturn(users).when(userDao).findAll();
        List<UserDto> result = userService.findAll();

        for (int i = 0; i < users.size(); i++) {
            assertEquals(userDtoMapper.userToUserDto(users.get(i)), result.get(i));
        }
    }

    @Test
    void updateUser() {
        doReturn(user).when(userDao).update(user);
        UserDto result = userService.update(userDto);
        assertEquals(userDtoMapper.userToUserDto(user), result);
    }

    @Test
    void deleteUser() {
        doReturn(true).when(userDao).delete(user.getId());
        assertTrue(userService.delete(userDto.getId()));
    }
}
