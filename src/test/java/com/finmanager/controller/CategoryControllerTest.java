package com.finmanager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.finmanager.dto.CategoryDto;
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

class CategoryControllerTest {

    private CategoryDto categoryDto;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private IService<CategoryDto> categoryService;

    @InjectMocks
    private CategoryController categoryController;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);

        categoryDto = new CategoryDto(1L, "categ", "oper", LocalDateTime.now(), LocalDateTime.now());
        mockMvc = MockMvcBuilders.standaloneSetup(categoryController)
                .build();
    }

    @Test
    void createCategory() throws Exception {
        doReturn(categoryDto).when(categoryService).create(categoryDto);

        objectMapper.registerModule(new JavaTimeModule());

        String resultJSON = objectMapper.writeValueAsString(categoryDto);

        mockMvc.perform(
                post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(resultJSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").value(categoryDto.getId()))
                .andExpect(jsonPath("name").value(categoryDto.getName()))
                .andExpect(jsonPath("description").value(categoryDto.getDescription()))
                .andDo(print());
    }

    @Test
    void findCategoryById() throws Exception {
        doReturn(categoryDto).when(categoryService).findById(categoryDto.getId());
        mockMvc.perform(get("/categories/" + categoryDto.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(categoryDto.getId()))
                .andExpect(jsonPath("name").value(categoryDto.getName()))
                .andExpect(jsonPath("description").value(categoryDto.getDescription()));
    }

    @Test
    void findAllCategories() throws Exception {
        List<CategoryDto> categoryDtos = Arrays.asList(categoryDto, new CategoryDto(2L, "descrrted", "opesdr", LocalDateTime.now(), LocalDateTime.now()));
        doReturn(categoryDtos).when(categoryService).findAll();

        mockMvc.perform(get("/categories/all").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(categoryDtos.get(0).getId()))
                .andExpect(jsonPath("$[0].name").value(categoryDtos.get(0).getName()))
                .andExpect(jsonPath("$[0].description").value(categoryDtos.get(0).getDescription()))
                .andExpect(jsonPath("$[1].id").value(categoryDtos.get(1).getId()))
                .andExpect(jsonPath("$[1].name").value(categoryDtos.get(1).getName()))
                .andExpect(jsonPath("$[1].description").value(categoryDtos.get(1).getDescription()))
                .andDo(print());
    }

    @Test
    void updateCategory() throws Exception {
        doReturn(categoryDto).when(categoryService).update(categoryDto);

        objectMapper.registerModule(new JavaTimeModule());

        String json = objectMapper.writeValueAsString(categoryDto);

        mockMvc.perform(put("/categories/" + categoryDto.getId()).contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(categoryDto.getId()))
                .andExpect(jsonPath("name").value(categoryDto.getName()))
                .andExpect(jsonPath("description").value(categoryDto.getDescription()));
    }

    @Test
    void deleteOperation() throws Exception {
        doReturn(true).when(categoryService).delete(categoryDto.getId());
        MvcResult result = mockMvc.perform(delete("/categories/" + categoryDto.getId()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        assertEquals("true", result.getResponse().getContentAsString());
    }
}
