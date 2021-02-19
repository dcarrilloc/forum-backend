package com.esliceu.forum.forum.controllers;

import com.auth0.jwt.interfaces.Claim;
import com.esliceu.forum.forum.entities.Reply;
import com.esliceu.forum.forum.services.ReplyService;
import com.esliceu.forum.forum.utils.customSerializers.ReplySerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ReplyController {
    Gson gson = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .registerTypeAdapter(Reply.class, new ReplySerializer())
            .create();

    @Autowired
    ReplyService replyService;

    @PostMapping("/topics/{id}/replies")
    public ResponseEntity<String> reply(@PathVariable(name = "id") Long topic_id, @RequestBody String payload, @RequestAttribute Map<String, Claim> userDetailsFromToken) {
        Long userid = Long.parseLong(userDetailsFromToken.get("sub").asString());
        Map<String, String> data = gson.fromJson(payload, HashMap.class);
        String content = data.get("content");

        Reply reply = replyService.reply(content, topic_id, userid);
        return new ResponseEntity<>(gson.toJson(reply), HttpStatus.OK);
    }
}
