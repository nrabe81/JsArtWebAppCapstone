package com.ecommerce.library.service.impl;

import com.ecommerce.library.dto.CategoryDto;
import com.ecommerce.library.model.Category;
import com.ecommerce.library.repository.CategoryRepository;
import com.ecommerce.library.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService
{
    @Autowired
    private CategoryRepository repo;

    //return all categories
    @Override
    public List<Category> findAll()
    {
        return repo.findAll();
    }

    //create a new category
    @Override
    public Category save(Category category)
    {
        try {
            Category categorySave = new Category(category.getName());
            return repo.save(categorySave);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return null;
        }

    }

    //find a category by it's id
    @Override
    public Category findById(Long id)
    {
        return repo.findById(id).get();
    }

    //update and add a new category
    @Override
    public Category update(Category category)
    {
        Category categoryUpdate = null;
        try {
            categoryUpdate = repo.findById(category.getId()).get();
            categoryUpdate.setName(category.getName());
            categoryUpdate.set_activated(category.is_activated());
            categoryUpdate.set_deleted(category.is_deleted());
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return repo.save(categoryUpdate);
    }

    //delete a category by using it's id and change activated to false
    @Override
    public void deleteById(Long id)
    {
        Category category = repo.getReferenceById(id);
        category.set_deleted(true);
        category.set_activated(false);
        repo.save(category);
    }

    //enable a category by it's id. Basically do the opposite of "deleteById"
    @Override
    public void enableById(Long id)
    {
        Category category = repo.getReferenceById(id);
        category.set_deleted(false);
        category.set_activated(true);
        repo.save(category);
    }

    @Override
    public List<Category> findAllByActivated() {
        return repo.findAllByActivated();
    }

    @Override
    public List<CategoryDto> getCategoryAndProduct() {
        return repo.getCategoryAndProduct();
    }
}
