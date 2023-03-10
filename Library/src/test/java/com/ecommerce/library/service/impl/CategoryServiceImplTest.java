package com.ecommerce.library.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.ecommerce.library.LibraryApplication;
import com.ecommerce.library.model.Category;
import com.ecommerce.library.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = LibraryApplication.class)
public class CategoryServiceImplTest {
    private CategoryRepository repo;
    private CategoryServiceImpl categoryService;

    @BeforeEach
    public void setUp() {
//        repo = new CategoryRepositoryStub();
        categoryService = new CategoryServiceImpl();
        //categoryService.repo = repo;
    }

    @Test
    public void testSave()
    {
        Category category = new Category("Test Category");
        Category savedCategory = categoryService.save(category);
        assertNotNull(savedCategory.getId());
        assertEquals(category.getName(), savedCategory.getName());
    }

    @Test
    public void testFindById() {
        Category category = new Category("Test Category");
        Category savedCategory = repo.save(category);
        Category foundCategory = categoryService.findById(savedCategory.getId());
        assertEquals(savedCategory.getId(), foundCategory.getId());
        assertEquals(savedCategory.getName(), foundCategory.getName());
    }

    @Test
    public void testUpdate() {
        Category category = new Category("Test Category");
        Category savedCategory = repo.save(category);
        savedCategory.setName("Updated Test Category");
        Category updatedCategory = categoryService.update(savedCategory);
        assertEquals(savedCategory.getId(), updatedCategory.getId());
        assertEquals(savedCategory.getName(), updatedCategory.getName());
    }

    @Test
    public void testDeleteById() {
        Category category = new Category("Test Category");
        Category savedCategory = repo.save(category);
        categoryService.deleteById(savedCategory.getId());
        Category deletedCategory = repo.findById(savedCategory.getId()).orElse(null);
        assertNotNull(deletedCategory);
        assertEquals(false, deletedCategory.is_activated());
        assertEquals(true, deletedCategory.is_deleted());
    }

    @Test
    public void testEnableById() {
        Category category = new Category("Test Category");
        Category savedCategory = repo.save(category);
        categoryService.deleteById(savedCategory.getId());
        categoryService.enableById(savedCategory.getId());
        Category enabledCategory = repo.findById(savedCategory.getId()).orElse(null);
        assertNotNull(enabledCategory);
        assertEquals(true, enabledCategory.is_activated());
        assertEquals(false, enabledCategory.is_deleted());
    }
}

