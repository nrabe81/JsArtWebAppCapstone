package com.ecommerce.admin.controller;

import com.ecommerce.library.model.Category;
import com.ecommerce.library.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CategoryControllerTest
{

    private CategoryService categoryService;
    private CategoryController categoryController;

    @Test
    public void testCategoriesWithoutPrincipal()
    {
        // Given
        categoryService = mock(CategoryService.class);
        categoryController = new CategoryController();
        Model model = mock(Model.class);
        Principal principal = null;

        // When
        String result = categoryController.categories(model, principal);

        // Then
        assertThat(result).isEqualTo("redirect:/login");
    }

    @Test
    public void testCategoriesWithPrincipal()
    {
        // Given
        categoryService = mock(CategoryService.class);
        categoryController = new CategoryController();
        Model model = mock(Model.class);
        Principal principal = mock(Principal.class);
        List<Category> categories = new ArrayList<>();
        categories.add(new Category());
        when(categoryService.findAll()).thenReturn(categories);

        // When
        String result = categoryController.categories(model, principal);

        // Then
        assertThat(result).isEqualTo("categories");
        assertThat(model.containsAttribute("categories")).isTrue();
        assertThat(model.getAttribute("categories")).isEqualTo(categories);
        assertThat(model.containsAttribute("size")).isTrue();
        assertThat(model.getAttribute("size")).isEqualTo(categories.size());
        assertThat(model.containsAttribute("title")).isTrue();
        assertThat(model.getAttribute("title")).isEqualTo("Category");
        assertThat(model.containsAttribute("categoryNew")).isTrue();
        assertThat(model.getAttribute("categoryNew")).isEqualTo(new Category());
    }

    @Test
    public void testAddCategory()
    {
        // Given
        categoryService = mock(CategoryService.class);
        categoryController = new CategoryController();
        Category category = new Category();
        RedirectAttributes attributes = mock(RedirectAttributes.class);

        // When
        String result = categoryController.add(category, attributes);

        // Then
        assertThat(result).isEqualTo("redirect:/categories");
        assertThat(attributes.getFlashAttributes().containsKey("success")).isTrue();
        assertThat(attributes.getFlashAttributes().get("success")).isEqualTo("Added successfully");
    }

    @Test
    public void testAddCategoryWithDuplicateName()
    {
        // Given
        categoryService = mock(CategoryService.class);
        categoryController = new CategoryController();
        Category category = new Category();
        RedirectAttributes attributes = mock(RedirectAttributes.class);
        when(categoryService.save(any(Category.class))).thenThrow(new DataIntegrityViolationException("Duplicate Name"));

        // When
        String result = categoryController.add(category, attributes);

        // Then
        assertThat(result).isEqualTo("redirect:/categories");
        assertThat(attributes.getFlashAttributes().containsKey("failed")).isTrue();
        assertThat(attributes.getFlashAttributes().get("failed")).isEqualTo("Duplicate Name");
    }

}
