package com.esliceu.forum.forum.services;

import com.esliceu.forum.forum.entities.Category;
import com.esliceu.forum.forum.repos.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

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
        return optionalCategory.orElse(null);
    }

    @Override
    public Category addCategory(String title, String description) {
        Category category = new Category();
        category.setTitle(title);
        category.setDescription(description);
        category.setSlug(generateSlug(title));
        category.setColor(generateRandomColor());
        return categoryRepo.save(category);
    }

    @Override
    public Category updateCategory(String slug, String title, String description) {
        Optional<Category> optionalCategory = categoryRepo.findBySlug(slug);
        if(optionalCategory.isPresent()) {
            Category category = optionalCategory.get();
            category.setTitle(title);
            category.setSlug(generateSlug(title));
            category.setDescription(description);
            return categoryRepo.save(category);
        }
        return null;
    }

    @Override
    public void deleteCategory(String slug) throws Exception {
        Optional<Category> optionalCategory = categoryRepo.findBySlug(slug);
        if(optionalCategory.isPresent()) {
            categoryRepo.delete(optionalCategory.get());
        } else {
            throw new Exception("Category not found");
        }
    }

    private String generateSlug(String title) {
        String slug = title.replaceAll("\\s", "-")
                .toLowerCase(Locale.ROOT);

        List<Category> existentSlugs = categoryRepo.findAllBySlug(slug);
        if(existentSlugs.size() > 0) {
            return slug + (existentSlugs.size() + 1);
        }
        return slug;
    }

    private String generateRandomColor() {
        int randomHue = ThreadLocalRandom.current().nextInt(0, 360 + 1);
        return "hsl(" + randomHue + ", 100%, 50%)";
    }


}
