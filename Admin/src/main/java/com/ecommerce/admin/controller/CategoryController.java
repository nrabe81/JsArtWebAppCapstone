package com.ecommerce.admin.controller;

import com.ecommerce.library.model.Category;
import com.ecommerce.library.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@Controller
public class CategoryController
{
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/categories")
    public String categories(Model model, Principal principal)
    {
        if(principal == null)
        {
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
        }
        catch (DataIntegrityViolationException ex)
        {
            ex.printStackTrace();
            attributes.addFlashAttribute("failed", "Duplicate Name");
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            attributes.addFlashAttribute("failed", "Failed. Try Again");
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
        }
        catch (DataIntegrityViolationException ex)
        {
            ex.printStackTrace();
            attributes.addFlashAttribute("failed", "Duplicate Name");
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            attributes.addFlashAttribute("failed", "Failed to Update, Server Error");
        }

        return "redirect:/categories";
    }

    @RequestMapping(value = "/delete-category", method = {RequestMethod.PUT, RequestMethod.GET})
    public String delete(Long id, RedirectAttributes attributes)
    {
        try {
            categoryService.deleteById(id);
            attributes.addFlashAttribute("success", "Disabled Successfully");
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            attributes.addFlashAttribute("failed", "Failed to Delete");
        }
        return "redirect:/categories";
    }

    @RequestMapping(value = "/enable-category", method = {RequestMethod.PUT, RequestMethod.GET})
    public String enable(Long id, RedirectAttributes attributes)
    {
        try {
            categoryService.enableById(id);
            attributes.addFlashAttribute("success", "Enabled Successfully");
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            attributes.addFlashAttribute("failed", "Failed to Enable");
        }
        return "redirect:/categories";
    }
}
