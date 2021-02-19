package com.esliceu.forum.forum.services;

import com.esliceu.forum.forum.entities.Category;
import com.esliceu.forum.forum.repos.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepo categoryRepo;

    @Override
    public List<Category> getAll() {
        return categoryRepo.findAll();
    }

    @Override
    public Category getCategoryBySlug(String slug) {
        Optional<Category> optionalCategory = categoryRepo.findBySlug(slug);
        if (optionalCategory.isPresent()) {
            return optionalCategory.get();
        }
        return null;
    }
}
