package com.Saransh.E_Commerce.service.category;

import com.Saransh.E_Commerce.Model.Category;

import java.util.List;

public interface CategoryService {
    Category getCategoryById(Long id);
    Category getCategoryByName(String name);
    List<Category> getAllCategories();
    Category addCategory(Category category);
    Category updateCategory(Category category,Long id);
    void deleteCategory(Long id);
}
