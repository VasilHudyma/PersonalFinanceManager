package com.finmanager.controller;


import com.finmanager.dto.CategoryDto;
import com.finmanager.dto.Transafer.ExistingRecord;
import com.finmanager.dto.Transafer.NewRecord;
import com.finmanager.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    private IService<CategoryDto> categoryService;

    @Autowired
    public CategoryController(IService<CategoryDto> categoryService) {
        this.categoryService = categoryService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public CategoryDto createCategory(@Validated(NewRecord.class) @RequestBody CategoryDto categoryDto) {
        return categoryService.create(categoryDto);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CategoryDto findCategoryById(@PathVariable("id") Long id) {
        return categoryService.findById(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CategoryDto> findAllCategories() {
        return categoryService.findAll();
    }


    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public CategoryDto updateCategory(@Validated(ExistingRecord.class) @PathVariable("id") Long id, @RequestBody CategoryDto categoryDto) {
        categoryDto.setId(id);
        return categoryService.update(categoryDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(value = "/{id}")
    public boolean deleteCategory(@PathVariable("id") Long id) {
        return categoryService.delete(id);
    }
}
