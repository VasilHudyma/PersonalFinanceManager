package com.finmanager.service;

import com.finmanager.dao.IDao;
import com.finmanager.dto.CategoryDto;
import com.finmanager.dtoMapper.CategoryDtoMapper;
import com.finmanager.model.Category;
import name.falgout.jeffrey.testing.junit.mockito.MockitoExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {
    private final CategoryDto categoryDto = new CategoryDto(1L, "catName", "catDescrp", LocalDateTime.now(), LocalDateTime.now());
    private CategoryDtoMapper categoryDtoMapper = new CategoryDtoMapper();

    @Mock
    private IDao<Category> categoryDao;

    @InjectMocks
    private CategoryService categoryService;

    private Category category;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);

        category = categoryDtoMapper.categoryDtoToCategory(categoryDto);
        categoryService = new CategoryService(categoryDao, categoryDtoMapper);
    }

    @Test
    void createCategoryTest() {
        doReturn(category).when(categoryDao).create(category);
        CategoryDto result = categoryService.create(categoryDto);
        assertEquals(categoryDtoMapper.categoryToCategoryDto(category), result);
    }

    @Test
    void findCategoryByIdTest() {
        doReturn(category).when(categoryDao).findById(category.getId());
        CategoryDto result = categoryService.findById(categoryDto.getId());
        assertEquals(categoryDtoMapper.categoryToCategoryDto(category), result);
    }

    @Test
    void findAllCategoriesTest() {
        List<Category> categories = Arrays.asList(category, new Category(2L, "catName", "catDescrp", LocalDateTime.now(), LocalDateTime.now()));
        doReturn(categories).when(categoryDao).findAll();

        List<CategoryDto> categoryDtos = categoryService.findAll();
        for (int i = 0; i < categories.size(); i++) {
            assertEquals(categoryDtoMapper.categoryToCategoryDto(categories.get(i)), categoryDtos.get(i));
        }
    }

    @Test
    void updateCategoryTest() {
        doReturn(category).when(categoryDao).update(category);
        CategoryDto result = categoryService.update(categoryDto);
        assertEquals(categoryDtoMapper.categoryToCategoryDto(category), result);
    }

    @Test
    void deleteCategoryTest() {
        doReturn(true).when(categoryDao).delete(category.getId());
        assertTrue(categoryService.delete(categoryDto.getId()));
    }
}
