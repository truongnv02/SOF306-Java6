package com.poly.truongnvph29176.service;

import com.poly.truongnvph29176.dto.request.CategoryRequest;
import com.poly.truongnvph29176.entity.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategory();
    Category createCategory(CategoryRequest categoryRequest);
    Category getCategoryById(String id) throws Exception;
    Category updateCategory(String id, CategoryRequest categoryRequest) throws Exception;
    void deleteCategory(String id);
}
