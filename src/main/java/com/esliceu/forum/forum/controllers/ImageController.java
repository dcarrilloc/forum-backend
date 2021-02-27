package com.esliceu.forum.forum.controllers;

import com.esliceu.forum.forum.services.UserService;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImageController {
    @Autowired
    UserService userService;

    @GetMapping(value = "/images/users/{userid}", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getUserImg(@PathVariable Long userid) {
        byte[] userImg = userService.getUserImage(userid);
        System.out.println(userImg);
        String img64Bytes = new String(userImg).split(",")[1];
        return Base64.decodeBase64(img64Bytes);
    }
}
