package com.esliceu.forum.forum.controllers;

import com.esliceu.forum.forum.entities.Category;
import com.esliceu.forum.forum.entities.Reply;
import com.esliceu.forum.forum.entities.Topic;
import com.esliceu.forum.forum.entities.User;
import com.esliceu.forum.forum.services.CategoryService;
import com.esliceu.forum.forum.services.TopicService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.bytebuddy.matcher.FilterableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CategoryController {
    Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

    @Autowired
    CategoryService categoryService;

    @Autowired
    TopicService topicService;

    @GetMapping("/categories")
    public ResponseEntity<String> categories() {
        List<Category> categories = categoryService.getAll();
        List<Map<String, Object>> response = new ArrayList<>();
        categories.forEach(category -> {
            Map<String, Object> entry = new HashMap<>();
            entry.put("color", category.getColor());
            entry.put("description", category.getDescription());
            entry.put("moderators", new ArrayList<>());
            entry.put("slug", category.getSlug());
            entry.put("title",  category.getTitle());
            entry.put("__v", 0);
            entry.put("_id", String.valueOf(category.getCategory_id()));

            response.add(entry);
        });
        return new ResponseEntity<>(gson.toJson(response), HttpStatus.OK);
    }

    @GetMapping("/categories/{slug}")
    public ResponseEntity<String> category(@PathVariable String slug) {
        Category category = categoryService.getCategoryBySlug(slug);
        Map<String, Object> response = new HashMap<>();
        response.put("color", category.getColor());
        response.put("description", category.getDescription());
        response.put("moderators", new ArrayList<>());
        response.put("slug", category.getSlug());
        response.put("title", category.getTitle());
        response.put("__v", 0);
        response.put("_id", String.valueOf(category.getCategory_id()));

        return new ResponseEntity<>(gson.toJson(response), HttpStatus.OK);
    }
}
