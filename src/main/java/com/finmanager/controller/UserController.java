package com.finmanager.controller;

import com.finmanager.dto.UserDto;
import com.finmanager.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }



    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDto findUserById(@PathVariable("id") Long id) {
        return userService.findById(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserDto> findAllUsers() {
        return userService.findAll();
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserDto updateUser(@PathVariable("id") Long id, @RequestBody UserDto userDto) {
        userDto.setId(id);
        return userService.update(userDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(value = "/{id}")
    public boolean deleteUser(@PathVariable("id") Long id) {
        return userService.delete(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDto findUserByEmail(@RequestParam("email") String email) {
        return userService.findUserByEmail(email);
    }
}
