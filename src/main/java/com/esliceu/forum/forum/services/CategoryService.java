package com.esliceu.forum.forum.services;

import com.esliceu.forum.forum.entities.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAll();

    Category getCategoryBySlug(String slug);
}
