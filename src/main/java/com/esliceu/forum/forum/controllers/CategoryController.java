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
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @PostMapping("/categories")
    public ResponseEntity<String> postCategory(@RequestBody String payload) {
        Map<String, String> data = gson.fromJson(payload, HashMap.class);
        String title = data.get("title");
        String description = data.get("description");
        Category category = categoryService.addCategory(title, description);
        return new ResponseEntity<>(gson.toJson(category), HttpStatus.OK);
    }

    @GetMapping("/categories/{slug}")
    public ResponseEntity<String> category(@PathVariable String slug) {
        Category category = categoryService.getCategoryBySlug(slug);
        return new ResponseEntity<>(gson.toJson(category), HttpStatus.OK);
    }

    @PutMapping("/categories/{slug}")
    public ResponseEntity<String> updateCategory(@PathVariable String slug, @RequestBody String payload) {
        Map<String, String> data = gson.fromJson(payload, HashMap.class);
        String title = data.get("title");
        String description = data.get("description");

        Category category = categoryService.updateCategory(slug, title, description);
        return new ResponseEntity<>(gson.toJson(category), HttpStatus.OK);
    }

    @DeleteMapping("/categories/{slug}")
    public ResponseEntity<String> deleteCategory(@PathVariable String slug) {
        try {
            categoryService.deleteCategory(slug);
            return new ResponseEntity<>(gson.toJson(true), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(gson.toJson(false), HttpStatus.METHOD_NOT_ALLOWED);
        }
    }
}
