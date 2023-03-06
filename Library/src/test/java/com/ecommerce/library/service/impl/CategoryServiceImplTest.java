package com.ecommerce.library.service.impl;

import com.ecommerce.library.model.Category;
import com.ecommerce.library.repository.CategoryRepository;
import com.ecommerce.library.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryServiceImplTest
{
//    private CategoryRepositoryStub categoryRepositoryStub;
//    private CategoryService categoryService;
//
//    @BeforeEach
//    public void setup() {
//        categoryRepositoryStub = new CategoryRepositoryStub();
//        categoryService = new CategoryService(categoryRepositoryStub);
//    }
//
//    @Test
//    public void saveCategory_success() {
//        // arrange
//        Category category = new Category();
//        category.setName("Test Category");
//
//        // act
//        Category result = categoryService.save(category);
//
//        // assert
//        assertNotNull(result);
//        assertEquals(1, result.getId());
//        assertEquals(category.getName(), result.getName());
//    }
//
//    @Test
//    public void saveCategory_withException() {
//        // arrange
//        CategoryRepositoryStub saveMethodThrowingStub = new CategoryRepositoryStub() {
//            @Override
//            public Category save(Category category) {
//                throw new RuntimeException("Test Exception");
//            }
//        };
//        CategoryService categoryService = new CategoryService(saveMethodThrowingStub);
//
//        Category category = new Category();
//        category.setName("Test Category");
//
//        // act
//        Category result = categoryService.save(category);
//
//        // assert
//        assertNull(result);
//    }
//
//    private class CategoryRepositoryStub implements CategoryRepository {
//        private Map<Long, Category> categories = new HashMap<>();
//        private long idCounter = 1;
//
//        @Override
//        public Category save(Category category) {
//            Category savedCategory = new Category(category.getName());
//            savedCategory.setId(idCounter++);
//            categories.put(savedCategory.getId(), savedCategory);
//            return savedCategory;
//        }
//    }

}