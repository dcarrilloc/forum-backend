package com.esliceu.forum.forum.controllers;

import com.auth0.jwt.interfaces.Claim;
import com.esliceu.forum.forum.entities.User;
import com.esliceu.forum.forum.services.TokenService;
import com.esliceu.forum.forum.services.UserService;
import com.esliceu.forum.forum.utils.customSerializers.UserSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@RestController
public class UserController {
    Gson gson = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .registerTypeAdapter(User.class, new UserSerializer())
            .create();

    @Autowired
    UserService userService;

    @Autowired
    TokenService tokenService;

    @GetMapping("/getprofile")
    public ResponseEntity<String> getprofile(@RequestAttribute Map<String, Claim> userDetailsFromToken) {
        User user = userService.getUserById(Long.parseLong(userDetailsFromToken.get("sub").asString()));
        return new ResponseEntity<>(gson.toJson(user), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody String payload) {
        Map<String, String> map = gson.fromJson(payload, HashMap.class);
        String email = map.get("email");
        String password = map.get("password");

        if (!userService.checkCredentials(email, password)) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "Incorrect email or password.");
            return new ResponseEntity<>(gson.toJson(errorResponse), HttpStatus.BAD_REQUEST);
        }
        return getStringResponseEntity(userService.getUserByEmail(email).getUser_id());
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody String payload) {
        Map<String, String> map = gson.fromJson(payload, HashMap.class);
        String email = map.get("email");
        String password = map.get("password");
        String name = map.get("name");
        String role = map.get("role");
        System.out.println(role);

        if (!userService.checkRegisterCredentials(email)) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Bad Request");
            error.put("message", "This user already exists");
            error.put("statusCode", 400);
            return new ResponseEntity<>(gson.toJson(error), HttpStatus.BAD_REQUEST);
        }

        User user = userService.register(email, password, name, role);

        if(map.get("moderateCategory") != null && !map.get("moderateCategory").equals("")) {
            userService.addModerator(user, map.get("moderateCategory"));
        }

        Map<String, String> response = new HashMap<>();
        response.put("message", "done");
        return new ResponseEntity<>(gson.toJson(response), HttpStatus.OK);
    }

    @PutMapping("/profile")
    public ResponseEntity<String> profile(@RequestBody String payload, @RequestAttribute Map<String, Claim> userDetailsFromToken) {
        Long userid = Long.parseLong(userDetailsFromToken.get("sub").asString());
        Map<String, String> map = gson.fromJson(payload, HashMap.class);
        String email = map.get("email");
        String name = map.get("name");
        String avatar = map.get("avatar");
        userService.updateProfile(userid, email, name);
        if(avatar != null && !avatar.equals("")) {
            userService.updateProfileImage(avatar, userid);
        }
        return getStringResponseEntity(userService.getUserByEmail(email).getUser_id());
    }

    @PutMapping("/profile/password")
    public ResponseEntity<String> password(@RequestBody String payload, @RequestAttribute Map<String, Claim> userDetailsFromToken) {
        Long userid = Long.parseLong(userDetailsFromToken.get("sub").asString());
        Map<String, String> map = gson.fromJson(payload, HashMap.class);
        String currentPassword = map.get("currentPassword");
        String newPassword = map.get("newPassword");

        try {
            userService.updatePassword(userid, currentPassword, newPassword);
        } catch (Exception e) {
            Map<String, String> badResponse = new HashMap<>();
            badResponse.put("message", "Your current password is wrong!");
            return new ResponseEntity<>(gson.toJson(badResponse), HttpStatus.UNAUTHORIZED);
        }
        return getStringResponseEntity(userid);
    }

    private ResponseEntity<String> getStringResponseEntity(Long userid) {
        User user = userService.getUserById(userid);

        Map<String, Object> response = new HashMap<>();
        response.put("token", tokenService.generateToken(userid));
        response.put("user", user);

        return new ResponseEntity<>(gson.toJson(response), HttpStatus.OK);
    }
}
