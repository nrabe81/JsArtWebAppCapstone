package com.ecommerce.admin.controller;

import com.ecommerce.library.dto.ProductDto;
import com.ecommerce.library.model.Category;
import com.ecommerce.library.service.CategoryService;
import com.ecommerce.library.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ProductControllerTest
{

    private ProductService productService;
    private CategoryService categoryService;
    private ProductController productController;

    @BeforeEach
    public void setUp()
    {
        productService = mock(ProductService.class);
        categoryService = mock(CategoryService.class);
        productController = new ProductController();
    }

    @Test
    public void testProducts()
    {
        Model model = mock(Model.class);
        Principal principal = mock(Principal.class);
        List<ProductDto> productDtoList = new ArrayList<>();
        productDtoList.add(new ProductDto());
        when(productService.findAll()).thenReturn(productDtoList);

        String result = productController.products(model, principal);

        assertEquals("products", result);
        verify(model).addAttribute("title", "Manage Product");
        verify(model).addAttribute("products", productDtoList);
        verify(model).addAttribute("size", productDtoList.size());
    }

    @Test
    public void testProductsPage()
    {
        Model model = mock(Model.class);
        Principal principal = mock(Principal.class);
        Page<ProductDto> products = mock(Page.class);
        when(productService.pageProducts(1)).thenReturn(products);

        String result = productController.productsPage(1, model, principal);

        assertEquals("products", result);
        verify(model).addAttribute("title", "Manage Product");
        verify(model).addAttribute("size", products.getSize());
        verify(model).addAttribute("totalPages", products.getTotalPages());
        verify(model).addAttribute("currentPage", 1);
        verify(model).addAttribute("products", products);
    }

    @Test
    public void testSearchProducts()
    {
        Model model = mock(Model.class);
        Principal principal = mock(Principal.class);
        Page<ProductDto> products = mock(Page.class);
        when(productService.searchProducts(1, "keyword")).thenReturn(products);

        String result = productController.searchProducts(1, "keyword", model, principal);

        assertEquals("result-products", result);
        verify(model).addAttribute("title", "Search Result");
        verify(model).addAttribute("products", products);
        verify(model).addAttribute("size", products.getSize());
        verify(model).addAttribute("currentPage", 1);
        verify(model).addAttribute("totalPages", products.getTotalPages());
    }

    @Test
    public void testAddProductForm()
    {
        Model model = mock(Model.class);
        Principal principal = mock(Principal.class);
        List<Category> categories = new ArrayList<>();
        categories.add(new Category());
        when(categoryService.findAllByActivated()).thenReturn(categories);

        String result = productController.addProductForm(model, principal);

        assertEquals("add-products", result);
        verify(model).addAttribute("categories", categories);
        verify(model).addAttribute("product", new ProductDto());
    }
}
