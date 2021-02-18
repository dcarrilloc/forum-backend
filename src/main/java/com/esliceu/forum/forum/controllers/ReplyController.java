package com.esliceu.forum.forum.controllers;

import com.auth0.jwt.interfaces.Claim;
import com.esliceu.forum.forum.entities.Reply;
import com.esliceu.forum.forum.entities.Topic;
import com.esliceu.forum.forum.services.ReplyService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ReplyController {
    Gson gson = new Gson();

    @Autowired
    ReplyService replyService;

    @PostMapping("/topics/{id}/replies")
    public ResponseEntity<String> reply(@PathVariable Long topic_id, @RequestBody String payload, @RequestAttribute Map<String, Claim> userDetailsFromToken) {
        String email = userDetailsFromToken.get("sub").asString();
        Map<String, String> data = gson.fromJson(payload, HashMap.class);
        String content = data.get("content");

        Reply reply = replyService.reply(content, topic_id, email);

        Map<String, Object> response = new HashMap<>();
        response.put("content", reply.getContent());
        response.put("createdAt", reply.getCreatedAt().toString());
        response.put("topic", String.valueOf(reply.getTopic().getTopic_id()));
        response.put("updatedAt", reply.getUpdatedAt().toString());
        response.put("user", reply.getUser());
        response.put("__v", 0);
        response.put("_id", String.valueOf(reply.getReply_id()));

        return new ResponseEntity<>(gson.toJson(response), HttpStatus.OK);
    }
}
