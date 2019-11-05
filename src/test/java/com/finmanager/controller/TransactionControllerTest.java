package com.finmanager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.finmanager.dto.TransactionDto;
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

class TransactionControllerTest {
    private TransactionDto transactionDto;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private IService<TransactionDto> transactionService;

    @InjectMocks
    private TransactionController transactionController;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);

        transactionDto = new TransactionDto(1L, 1L, 1L,1L, 100.1, "transDescrpt", LocalDateTime.now(), LocalDateTime.now());
        mockMvc = MockMvcBuilders.standaloneSetup(transactionController)
                .build();
    }

    @Test
    void createCategory() throws Exception {
        doReturn(transactionDto).when(transactionService).create(transactionDto);

        objectMapper.registerModule(new JavaTimeModule());

        String resultJSON = objectMapper.writeValueAsString(transactionDto);

        mockMvc.perform(
                post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(resultJSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").value(transactionDto.getId()))
                .andExpect(jsonPath("categoryId").value(transactionDto.getCategoryId()))
                .andExpect(jsonPath("operationId").value(transactionDto.getOperationId()))
                .andExpect(jsonPath("sum").value(transactionDto.getSum()))
                .andExpect(jsonPath("description").value(transactionDto.getDescription()))
                .andDo(print());
    }

    @Test
    void findCategoryById() throws Exception {
        doReturn(transactionDto).when(transactionService).findById(transactionDto.getId());
        mockMvc.perform(get("/transactions/" + transactionDto.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(transactionDto.getId()))
                .andExpect(jsonPath("categoryId").value(transactionDto.getCategoryId()))
                .andExpect(jsonPath("operationId").value(transactionDto.getOperationId()))
                .andExpect(jsonPath("sum").value(transactionDto.getSum()))
                .andExpect(jsonPath("description").value(transactionDto.getDescription()));
    }

    @Test
    void findAllCategories() throws Exception {
        List<TransactionDto> transactionDtos = Arrays.asList(transactionDto, new TransactionDto(12L, 21L, 21L,21L, 1200.1, "transDescrpt", LocalDateTime.now(), LocalDateTime.now()));
        doReturn(transactionDtos).when(transactionService).findAll();

        mockMvc.perform(get("/transactions/all").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(transactionDtos.get(0).getId()))
                .andExpect(jsonPath("$[0]operationId").value(transactionDtos.get(0).getOperationId()))
                .andExpect(jsonPath("$[0]sum").value(transactionDtos.get(0).getSum()))
                .andExpect(jsonPath("$[0].description").value(transactionDtos.get(0).getDescription()))
                .andExpect(jsonPath("$[1].id").value(transactionDtos.get(1).getId()))
                .andExpect(jsonPath("$[1]operationId").value(transactionDtos.get(1).getOperationId()))
                .andExpect(jsonPath("$[1]sum").value(transactionDtos.get(1).getSum()))
                .andExpect(jsonPath("$[1].description").value(transactionDtos.get(1).getDescription()))
                .andDo(print());
    }

    @Test
    void updateCategory() throws Exception {
        doReturn(transactionDto).when(transactionService).update(transactionDto);

        objectMapper.registerModule(new JavaTimeModule());

        String json = objectMapper.writeValueAsString(transactionDto);

        mockMvc.perform(put("/transactions/" + transactionDto.getId()).contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(transactionDto.getId()))
                .andExpect(jsonPath("categoryId").value(transactionDto.getCategoryId()))
                .andExpect(jsonPath("operationId").value(transactionDto.getOperationId()))
                .andExpect(jsonPath("sum").value(transactionDto.getSum()))
                .andExpect(jsonPath("description").value(transactionDto.getDescription()));
    }

    @Test
    void deleteOperation() throws Exception {
        doReturn(true).when(transactionService).delete(transactionDto.getId());
        MvcResult result = mockMvc.perform(delete("/transactions/" + transactionDto.getId()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        assertEquals("true", result.getResponse().getContentAsString());
    }
}
