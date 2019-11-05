package com.finmanager.dtoMapper;

import com.finmanager.config.ProfileConfig;
import com.finmanager.dto.CategoryDto;
import com.finmanager.model.Category;
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
class CategoryDtoMapperTest {
    private Category category;
    private CategoryDto categoryDto;

    @Autowired
    private CategoryDtoMapper categoryDtoMapper;

    @BeforeEach
    void init() {
        category = new Category(1L, "catName", "catDescrp", LocalDateTime.now(), LocalDateTime.now());
        categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
        categoryDto.setDescription(category.getDescription());
        categoryDto.setCreatedDate(category.getCreatedDate());
        categoryDto.setUpdatedDate(category.getUpdatedDate());
    }

    @Test
    void categoryToCategoryDtoTest() {
        assertEquals(categoryDto, categoryDtoMapper.categoryToCategoryDto(category));
    }

    @Test
    void categoryDtoToCategoryTest() {
        assertEquals(category, categoryDtoMapper.categoryDtoToCategory(categoryDto));
    }
}
