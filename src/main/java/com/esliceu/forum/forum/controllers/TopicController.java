package com.esliceu.forum.forum.controllers;

import com.esliceu.forum.forum.entities.Topic;
import com.esliceu.forum.forum.services.CategoryService;
import com.esliceu.forum.forum.services.TopicService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
public class TopicController {
    Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

    @Autowired
    CategoryService categoryService;

    @Autowired
    TopicService topicService;

    @GetMapping("/topics/{id}")
    public ResponseEntity<String> topic_id(@PathVariable Long id) {
        Topic topic = topicService.getTopicById(id);
        topicService.addView(id);

        Map<String, Object> response = new HashMap<>();
        response.put("category", String.valueOf(topic.getCategory().getCategory_id()));
        response.put("content", topic.getContent());
        response.put("createdAt", topic.getCreatedAt().toString());
        response.put("id", String.valueOf(topic.getTopic_id()));
        response.put("numberOfReplies", topic.getReplies().size());
        response.put("title", topic.getTitle());
        response.put("updatedAt", topic.getUpdatedAt().toString());
        response.put("user", topic.getUser());
        response.put("views", topic.getViews());
        response.put("__v", 0);
        response.put("_id", String.valueOf(topic.getTopic_id()));

        return new ResponseEntity<>(gson.toJson(response), HttpStatus.OK);
    }

    @GetMapping("/categories/{slug}/topics")
    public ResponseEntity<String> category_topics(@PathVariable String slug) {
        List<Topic> topics = topicService.getTopicFromCategory(slug);
        List<Map<String, Object>> response = new ArrayList<>();
        topics.forEach(topic -> {
            Map<String, Object> entry = new HashMap<>();
            entry.put("category", topic.getCategory());
            entry.put("content", topic.getContent());
            entry.put("createdAt", topic.getCreatedAt().toString());
            entry.put("id", String.valueOf(topic.getTopic_id()));
            entry.put("numberOfReplies", topic.getReplies().size());
            entry.put("replies", null);
            entry.put("title", topic.getTitle());
            entry.put("updatedAt", topic.getUpdatedAt().toString());
            entry.put("user", topic.getUser());
            entry.put("views", topic.getViews());
            entry.put("__v", 0);
            entry.put("_id", String.valueOf(topic.getTopic_id()));

            response.add(entry);
        });
        return new ResponseEntity<>(gson.toJson(response), HttpStatus.OK);
    }
}
