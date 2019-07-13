package com.finmanager.dao;

import com.finmanager.exceptions.DbOperationException;
import com.finmanager.exceptions.NotFoundException;
import com.finmanager.model.Category;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CategoryDaoImplTest extends BaseTest {

    @Autowired
    private CategoryDAOImpl categoryDAO;

    private Category category = new Category(Long.MAX_VALUE, "newCategory", "some description", LocalDateTime.now(), LocalDateTime.now());

    @BeforeEach
    void createTestOperation() {
        category = categoryDAO.create(category);
    }

    @Test
    void createCategory() {
        assertEquals(category, categoryDAO.create(category));
    }

    @Test
    void createCategory_nullValue() {
        category.setName(null);
        assertThrows(DbOperationException.class, () -> categoryDAO.create(category));
    }

    @Test
    void findCategoryById() {
        assertEquals(category, categoryDAO.findById(category.getId()));
    }

    @Test
    void findCategoryById_invalidId() {
        assertThrows(NotFoundException.class, () -> categoryDAO.findById(Long.MAX_VALUE));
    }

    @Test
    void findAllCategories() {
        cleanCategories();
        List<Category> categories = Arrays.asList(categoryDAO.create(category),
                categoryDAO.create(new Category(2L, "sec", "secdesc", LocalDateTime.now(), LocalDateTime.now())),
                categoryDAO.create(new Category(3L, "third", "thirddesc", LocalDateTime.now(), LocalDateTime.now())));

        List<Category> categoriesFromDb = categoryDAO.findAll();

        for (int i = 0; i < categories.size(); i++) {
            assertEquals(categories.get(i), categoriesFromDb.get(i));
        }
    }

    @Test
    void updateCategory() {
        category.setName("updated");
        category = categoryDAO.update(category);
        assertEquals(category, categoryDAO.findById(category.getId()));
    }

    @Test
    void updateCategory_categoryNotExist() {
        category.setId(Long.MAX_VALUE);
        assertThrows(NotFoundException.class, () -> categoryDAO.update(category));
    }

    @Test
    void updateCategory_nullValue() {
        category.setName(null);
        assertThrows(DbOperationException.class, () -> categoryDAO.update(category));
    }

    @Test
    void deleteCategory() {
        assertTrue(categoryDAO.delete(category.getId()));
    }

    @Test
    void deleteCategory_invalidId() {
        assertThrows(NotFoundException.class, () -> categoryDAO.delete(Long.MAX_VALUE));
    }

    @AfterEach
    void cleanCategories() {
        clearAllTables();
    }
}
