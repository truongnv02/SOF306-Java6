package com.poly.truongnvph29176.service.impl;

import com.poly.truongnvph29176.dto.request.CategoryRequest;
import com.poly.truongnvph29176.entity.Category;
import com.poly.truongnvph29176.exception.DataNotFoundException;
import com.poly.truongnvph29176.repository.CategoryRepository;
import com.poly.truongnvph29176.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }

    @Override
    public Category createCategory(CategoryRequest categoryRequest) {
        String idCategory = categoryRequest.getId();
        Category categoryId = categoryRepository.findById(categoryRequest.getId()).orElse(null);
        if (categoryId != null) {
            throw new RuntimeException("id Category đã tồn tại");
        }
        Category category = Category.builder()
                .id(idCategory)
                .name(categoryRequest.getName())
                .build();
        return categoryRepository.save(category);
    }

    @Override
    public Category getCategoryById(String id) throws Exception {
        return categoryRepository.findById(id).orElseThrow(() ->
                    new DataNotFoundException("Cannot find with id = " + id)
                );
    }

    @Override
    public Category updateCategory(String id, CategoryRequest categoryRequest) throws Exception {
        Category category = getCategoryById(id);
        if(category != null) {
            category.setName(categoryRequest.getName());
            return categoryRepository.save(category);
        }
        return null;
    }

    @Override
    public void deleteCategory(String id) {
        categoryRepository.deleteById(id);
    }
}
