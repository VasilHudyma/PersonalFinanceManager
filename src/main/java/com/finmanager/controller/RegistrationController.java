package com.finmanager.controller;

import com.finmanager.dto.Transafer.NewRecord;
import com.finmanager.dto.UserDto;
import com.finmanager.service.IUserService;
import com.finmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping("/registration")
public class RegistrationController {

    private IUserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    public UserDto registerUser(@Validated(NewRecord.class) @RequestBody UserDto userDto) {
        return userService.create(userDto);
    }
}
