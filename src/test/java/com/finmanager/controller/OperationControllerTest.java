package com.finmanager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.finmanager.dto.OperationDto;
import com.finmanager.service.IService;
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


class OperationControllerTest {

    private OperationDto operationDto;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private IService<OperationDto> operationService;

    @InjectMocks
    private OperationController operationController;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);

        operationDto = new OperationDto(1L, "oper", LocalDateTime.now(), LocalDateTime.now());
        mockMvc = MockMvcBuilders.standaloneSetup(operationController)
                .build();
    }

    @Test
    void createOperation() throws Exception {
        doReturn(operationDto).when(operationService).create(operationDto);

        objectMapper.registerModule(new JavaTimeModule());

        String resultJSON = objectMapper.writeValueAsString(operationDto);

        mockMvc.perform(
                post("/operations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(resultJSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").value(operationDto.getId()))
                .andExpect(jsonPath("name").value(operationDto.getName()))
                .andDo(print());

    }

    @Test
    void findOperationById() throws Exception {
        doReturn(operationDto).when(operationService).findById(operationDto.getId());
        mockMvc.perform(get("/operations/" + operationDto.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(operationDto.getId()))
                .andExpect(jsonPath("name").value(operationDto.getName()));
    }

    @Test
    void findAllOperations() throws Exception {
        List<OperationDto> operationDtos = Arrays.asList(operationDto, new OperationDto(2L, "opesdr", LocalDateTime.now(), LocalDateTime.now()));
        doReturn(operationDtos).when(operationService).findAll();

        mockMvc.perform(get("/operations/all").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(operationDtos.get(0).getId()))
                .andExpect(jsonPath("$[0].name").value(operationDtos.get(0).getName()))
                .andExpect(jsonPath("$[1].id").value(operationDtos.get(1).getId()))
                .andExpect(jsonPath("$[1].name").value(operationDtos.get(1).getName()))
                .andDo(print());
    }

    @Test
    void updateOperation() throws Exception {
        doReturn(operationDto).when(operationService).update(operationDto);

        objectMapper.registerModule(new JavaTimeModule());

        String json = objectMapper.writeValueAsString(operationDto);

        mockMvc.perform(put("/operations/" + operationDto.getId()).contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(operationDto.getId()))
                .andExpect(jsonPath("name").value(operationDto.getName()));
    }

    @Test
    void deleteOperation() throws Exception {
        doReturn(true).when(operationService).delete(operationDto.getId());
        MvcResult result = mockMvc.perform(delete("/operations/" + operationDto.getId()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        assertEquals("true", result.getResponse().getContentAsString());
    }
}
