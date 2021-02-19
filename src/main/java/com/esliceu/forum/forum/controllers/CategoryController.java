package com.esliceu.forum.forum.controllers;

import com.esliceu.forum.forum.entities.Category;
import com.esliceu.forum.forum.services.CategoryService;
import com.esliceu.forum.forum.services.TopicService;
import com.esliceu.forum.forum.utils.customSerializers.CategorySerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CategoryController {
    Gson gson = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .registerTypeAdapter(Category.class, new CategorySerializer())
            .create();

    @Autowired
    CategoryService categoryService;

    @Autowired
    TopicService topicService;

    @GetMapping("/categories")
    public ResponseEntity<String> categories() {
        List<Category> categories = categoryService.getAll();
        return new ResponseEntity<>(gson.toJson(categories), HttpStatus.OK);
    }

    @GetMapping("/categories/{slug}")
    public ResponseEntity<String> category(@PathVariable String slug) {
        Category category = categoryService.getCategoryBySlug(slug);
        return new ResponseEntity<>(gson.toJson(category), HttpStatus.OK);
    }
}
