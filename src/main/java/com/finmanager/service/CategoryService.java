package com.finmanager.service;

import com.finmanager.dao.IDao;
import com.finmanager.dto.CategoryDto;
import com.finmanager.dtoMapper.CategoryDtoMapper;
import com.finmanager.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService implements IService<CategoryDto> {

    private IDao<Category> categoryDao;
    private CategoryDtoMapper categoryDtoMapper;

    @Autowired
    public CategoryService(IDao<Category> categoryDao, CategoryDtoMapper categoryDtoMapper) {
        this.categoryDao = categoryDao;
        this.categoryDtoMapper = categoryDtoMapper;
    }

    @Override
    public CategoryDto create(CategoryDto categoryDto) {
        return categoryDtoMapper.categoryToCategoryDto(categoryDao.create(categoryDtoMapper.categoryDtoToCategory(categoryDto)));
    }

    @Override
    public CategoryDto findById(Long id) {
        return categoryDtoMapper.categoryToCategoryDto(categoryDao.findById(id));
    }

    @Override
    public List<CategoryDto> findAll() {
        return categoryDao.findAll().stream().map(categoryDtoMapper::categoryToCategoryDto).collect(Collectors.toList());
    }

    @Override
    public CategoryDto update(CategoryDto categoryDto) {
        return categoryDtoMapper.categoryToCategoryDto(categoryDao.update(categoryDtoMapper.categoryDtoToCategory(categoryDto)));
    }

    @Override
    public boolean delete(Long id) {
        return categoryDao.delete(id);
    }
}
