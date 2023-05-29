package com.projekt.projekt.Services;

import com.projekt.projekt.Notes.Category;
import com.projekt.projekt.Repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    final
    CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    public Optional<Category> getById(Long id) {
        return categoryRepository.findById(id);
    }
    public List<Category> getAllCategories(){
        return categoryRepository.findAll();
    }
}
