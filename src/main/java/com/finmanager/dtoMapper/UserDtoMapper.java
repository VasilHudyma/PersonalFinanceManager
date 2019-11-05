package com.finmanager.dtoMapper;

import com.finmanager.dto.UserDto;
import com.finmanager.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserDtoMapper {
    public User userDtoToUser(UserDto userDto) {
        return new User(userDto.getId(), userDto.getEmail(), userDto.getPassword(), userDto.getName(), userDto.getSurname(),
                userDto.getPhone(), userDto.getRole(), userDto.getCreatedDate(), userDto.getUpdatedDate());
    }

    public UserDto userToUserDto(User user) {
        return new UserDto(user.getId(), user.getEmail(), user.getPassword(), user.getName(), user.getSurname(),
                user.getPhone(), user.getRole(), user.getCreatedDate(), user.getUpdatedDate());
    }
}
