package com.esliceu.forum.forum.controllers;

import com.esliceu.forum.forum.entities.User;
import com.esliceu.forum.forum.services.TokenService;
import com.esliceu.forum.forum.services.UserService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins="http://localhost:8081")
@RestController
public class UserController {
    Gson gson = new Gson();

    @Autowired
    UserService userService;

    @Autowired
    TokenService tokenService;

    @CrossOrigin(origins = "http://localhost:8081/")
    @GetMapping("/getprofile")
    public ResponseEntity<String> getprofile(@RequestAttribute String email) {
        User user = userService.getUserByEmail(email);
        Map<String, Object> response = new HashMap<>();
        response.put("avatarUrl", user.getAvatar());
        response.put("email", user.getEmail());
        response.put("id", String.valueOf(user.getUser_id()));
        response.put("name", user.getName());
        response.put("permissions", new ArrayList<>());
        response.put("role", user.getRole());
        response.put("__v", 0);
        response.put("_id", String.valueOf(user.getUser_id()));

        return new ResponseEntity<>(gson.toJson(response), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody String payload) {
        Map<String, String> map = gson.fromJson(payload, HashMap.class);
        String email = map.get("email");
        String password = map.get("password");

        if(!userService.checkCredentials(email, password)) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "Incorrect email or password.");
            return new ResponseEntity<>(gson.toJson(errorResponse), HttpStatus.BAD_REQUEST);
        }

        User user = userService.getUserByEmail(email);
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("avatarUrl", user.getAvatar());
        userMap.put("email", user.getEmail());
        userMap.put("id", String.valueOf(user.getUser_id()));
        userMap.put("name", user.getName());
        userMap.put("permissions", new ArrayList<>());
        userMap.put("role", user.getRole());
        userMap.put("__v", 0);
        userMap.put("_id", String.valueOf(user.getUser_id()));

        Map<String, Object> response = new HashMap<>();
        response.put("token", tokenService.generateToken(email));
        response.put("user", userMap);

        return new ResponseEntity<>(gson.toJson(response), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody String payload) {
        Map<String, String> map = gson.fromJson(payload, HashMap.class);
        String email = map.get("email");
        String password = map.get("password");
        String moderateCategory = map.get("moderateCategory");
        String name = map.get("name");
        String role = map.get("role");

        if(!userService.checkRegisterCredentials(email, password, moderateCategory, name, role)) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Bad Request");
            error.put("message", "This user already exists");
            error.put("statusCode", 400);
            return new ResponseEntity<>(gson.toJson(error), HttpStatus.BAD_REQUEST);
        }

        userService.register(email, password, moderateCategory, name, role);

        Map<String, String> response = new HashMap<>();
        response.put("message", "done");
        return new ResponseEntity<>(gson.toJson(response), HttpStatus.OK);
    }
}
