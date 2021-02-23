package com.esliceu.forum.forum.controllers;

import com.esliceu.forum.forum.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImageController {
    @Autowired
    UserService userService;

    @GetMapping("/images/users/{userid}")
    public byte[] getUserImg(@PathVariable Long userid) {
        System.out.println("Hola!");
        return userService.getUserImage(userid);
    }
}
