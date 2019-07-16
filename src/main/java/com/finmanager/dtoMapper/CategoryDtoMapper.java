package com.finmanager.dtoMapper;

import com.finmanager.dto.CategoryDto;
import com.finmanager.model.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryDtoMapper {

    public CategoryDto categoryToCategoryDto(Category category) {
        return new CategoryDto(category.getId(), category.getName(), category.getDescription(), category.getCreatedDate(), category.getUpdatedDate());
    }

    public Category categoryDtoToCategory(CategoryDto categoryDto) {
        return new Category(categoryDto.getId(), categoryDto.getName(), categoryDto.getDescription(), categoryDto.getCreatedDate(), categoryDto.getUpdatedDate());
    }
}
