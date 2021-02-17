package com.esliceu.forum.forum.controllers;

import com.esliceu.forum.forum.services.CategoryService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CategoryController {
    Gson gson = new Gson();

    @Autowired
    CategoryService categoryService;

    @GetMapping("/categories")
    public ResponseEntity<String> categories() {
        List categories = categoryService.getAll();
        return new ResponseEntity<>(gson.toJson(categories), HttpStatus.ACCEPTED);
    }
}
