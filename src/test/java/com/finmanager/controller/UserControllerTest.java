package com.finmanager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.finmanager.dto.UserDto;
import com.finmanager.model.Role;
import com.finmanager.service.IUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest {
    private UserDto userDto;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private IUserService userService;

    @InjectMocks
    private UserController userController;


    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
        userDto = new UserDto(4L, "mail@mail.com", "Password",
                "name", "surname", "9087238", Role.ADMIN, LocalDateTime.now(), LocalDateTime.now());
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .build();
    }

    @Test
    void findUserById() throws Exception {
        doReturn(userDto).when(userService).findById(userDto.getId());
        mockMvc.perform(get("/users/" + userDto.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(userDto.getId()))
                .andExpect(jsonPath("email").value(userDto.getEmail()))
                .andExpect(jsonPath("name").value(userDto.getName()))
                .andExpect(jsonPath("surname").value(userDto.getSurname()))
                .andExpect(jsonPath("role").value(userDto.getRole().toString()));
    }

    @Test
    void findUserByEmail() throws Exception {
        doReturn(userDto).when(userService).findUserByEmail(userDto.getEmail());
        mockMvc.perform(get("/users/?=email=" + userDto.getEmail())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(userDto.getId()))
                .andExpect(jsonPath("email").value(userDto.getEmail()))
                .andExpect(jsonPath("name").value(userDto.getName()))
                .andExpect(jsonPath("surname").value(userDto.getSurname()))
                .andExpect(jsonPath("role").value(userDto.getRole().toString()));
    }

    @Test
    void findAllUsers() throws Exception {
        List<UserDto> userDtos = Arrays.asList(userDto,
                new UserDto(5L, "5mail@mail.com", "Password",
                        "name", "surname", "9087238", Role.USER, LocalDateTime.now(), LocalDateTime.now()));
        doReturn(userDtos).when(userService).findAll();
        mockMvc.perform(get("/users/all").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(userDtos.get(0).getId()))
                .andExpect(jsonPath("$[0].name").value(userDtos.get(0).getName()))
                .andExpect(jsonPath("$[0].email").value(userDtos.get(0).getEmail()))
                .andExpect(jsonPath("$[1].id").value(userDtos.get(1).getId()))
                .andExpect(jsonPath("$[1].name").value(userDtos.get(1).getName()))
                .andExpect(jsonPath("$[1].email").value(userDtos.get(1).getEmail()))
                .andDo(print());
    }

    @Test
    void updateUser() throws Exception {
        doReturn(userDto).when(userService).update(userDto);
        objectMapper.registerModule(new JavaTimeModule());

        String json = objectMapper.writeValueAsString(userDto);

        mockMvc.perform(put("/users/" + userDto.getId())
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(userDto.getId()))
                .andExpect(jsonPath("email").value(userDto.getEmail()))
                .andExpect(jsonPath("name").value(userDto.getName()))
                .andExpect(jsonPath("surname").value(userDto.getSurname()))
                .andExpect(jsonPath("role").value(userDto.getRole().toString()));
    }

    @Test
    void deleteUser() throws Exception {
        doReturn(true).when(userService).delete(userDto.getId());
        MvcResult result = mockMvc.perform(delete("/users/" + userDto.getId()).
                contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        assertEquals("true", result.getResponse().getContentAsString());
    }
}
