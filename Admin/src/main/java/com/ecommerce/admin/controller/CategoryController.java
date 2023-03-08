package com.ecommerce.admin.controller;

import com.ecommerce.library.model.Category;
import com.ecommerce.library.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;


@Controller
public class CategoryController
{
    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/categories")
    public String categories(Model model, Principal principal)
    {
        if(principal == null)
        {
            LOGGER.warn("Return to Login");
            return "redirect:/login";
        }
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("size", categories.size());
        model.addAttribute("title", "Category");
        model.addAttribute("categoryNew", new Category());
        return "categories";
    }

    @PostMapping("/add-category")
    public String add(@ModelAttribute("categoryNew") Category category, RedirectAttributes attributes)
    {
        try {
            categoryService.save(category);
            attributes.addFlashAttribute("success", "Added successfully");
            LOGGER.info("Added successfully");
        }
        catch (DataIntegrityViolationException ex)
        {
            ex.printStackTrace();
            attributes.addFlashAttribute("failed", "Duplicate Name");
            LOGGER.error("Duplicate Name");
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            attributes.addFlashAttribute("failed", "Failed. Try Again");
            LOGGER.error("Failed. Try Again");
        }
        return "redirect:/categories";
    }

    @RequestMapping(value = "/findById", method = {RequestMethod.PUT, RequestMethod.GET})
    @ResponseBody
    public Category findById(Long id)
    {
        return categoryService.findById(id);
    }


    //Instructions for updating a category's name under "category" page
    @GetMapping("/update-category")
    public String update(Category category, RedirectAttributes attributes)
    {
        try {
            categoryService.update(category);
            attributes.addFlashAttribute("success", "Updated Successfully");
            LOGGER.info("Updated Successfully");
        }
        catch (DataIntegrityViolationException ex)
        {
            ex.printStackTrace();
            attributes.addFlashAttribute("failed", "Duplicate Name");
            LOGGER.error("Duplicate Name");
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            attributes.addFlashAttribute("failed", "Failed to Update, Server Error");
            LOGGER.error("Failed to Update, Server Error");
        }

        return "redirect:/categories";
    }

    @RequestMapping(value = "/delete-category", method = {RequestMethod.PUT, RequestMethod.GET})
    public String delete(Long id, RedirectAttributes attributes)
    {
        try {
            categoryService.deleteById(id);
            attributes.addFlashAttribute("success", "Disabled Successfully");
            LOGGER.info("Disabled Successfully");
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            attributes.addFlashAttribute("failed", "Failed to Delete");
            LOGGER.error("Failed to Delete");
        }
        return "redirect:/categories";
    }

    @RequestMapping(value = "/enable-category", method = {RequestMethod.PUT, RequestMethod.GET})
    public String enable(Long id, RedirectAttributes attributes)
    {
        try {
            categoryService.enableById(id);
            attributes.addFlashAttribute("success", "Enabled Successfully");
            LOGGER.info("Enabled Successfully");
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            attributes.addFlashAttribute("failed", "Failed to Enable");
            LOGGER.error("Failed to Enable");
        }
        return "redirect:/categories";
    }
}
