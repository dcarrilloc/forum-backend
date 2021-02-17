package com.esliceu.forum.forum.controllers;

import com.esliceu.forum.forum.services.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReplyController {
    @Autowired
    ReplyService replyService;

    @PostMapping("/topics/{id}/replies")
    public ResponseEntity<String> reply() {
        return null;
    }
}
