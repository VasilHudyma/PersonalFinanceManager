package com.finmanager.dtoMapper;

import com.finmanager.config.ProfileConfig;
import com.finmanager.dto.UserDto;
import com.finmanager.model.Role;
import com.finmanager.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {ProfileConfig.class})
class UserDtoMapperTest {
    private User user;
    private UserDto userDto;

    @Autowired
    private UserDtoMapper userDtoMapper;

    @BeforeEach
    void init() {
        user = new User(4L, "mail@mail.com", "Password",
                "name", "surname", "9087238", Role.ADMIN, LocalDateTime.now(), LocalDateTime.now());
        userDto = new UserDto(user.getId(), user.getEmail(), user.getPassword(), user.getName(), user.getSurname(), user.getPhone(), user.getRole(), user.getCreatedDate(), user.getUpdatedDate());
    }

    @Test
    void UserToUserDtoTest() {
        assertEquals(userDto, userDtoMapper.userToUserDto(user));
    }

    @Test
    void UserDtoToUserTest() {
        assertEquals(user, userDtoMapper.userDtoToUser(userDto));
    }
}
