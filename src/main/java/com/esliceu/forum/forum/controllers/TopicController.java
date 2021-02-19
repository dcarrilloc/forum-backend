package com.esliceu.forum.forum.controllers;

import com.auth0.jwt.interfaces.Claim;
import com.esliceu.forum.forum.entities.Topic;
import com.esliceu.forum.forum.services.CategoryService;
import com.esliceu.forum.forum.services.TopicService;
import com.esliceu.forum.forum.utils.customSerializers.AddTopicSerializer;
import com.esliceu.forum.forum.utils.customSerializers.Category_TopicSerializer;
import com.esliceu.forum.forum.utils.customSerializers.TopicSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TopicController {
    Gson gson = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .registerTypeAdapter(Topic.class, new TopicSerializer())
            .create();

    @Autowired
    CategoryService categoryService;

    @Autowired
    TopicService topicService;

    @GetMapping("/topics/{id}")
    public ResponseEntity<String> topic_id(@PathVariable Long id) {
        Topic topic = topicService.getTopicById(id);
        topicService.addView(id);
        return new ResponseEntity<>(gson.toJson(topic), HttpStatus.OK);
    }

    @PutMapping("/topics/{id}")
    public ResponseEntity<String> udpateTopic(@PathVariable Long id, @RequestBody String payload) {
        Map<String, String> data = gson.fromJson(payload, HashMap.class);
        String categorySlug = data.get("category");
        String content = data.get("content");
        String title = data.get("title");
        Topic topic = topicService.updateTopic(id, categorySlug, content, title);
        return new ResponseEntity<>(gson.toJson(topic), HttpStatus.OK);
    }

    @DeleteMapping("/topics/{id}")
    public ResponseEntity<String> deleteTopic(@PathVariable Long id) {
        try {
            topicService.deleteTopic(id);
            return new ResponseEntity<>(gson.toJson(true), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(gson.toJson(false), HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @GetMapping("/categories/{slug}/topics")
    public ResponseEntity<String> category_topics(@PathVariable String slug) {
        List<Topic> topics = topicService.getTopicFromCategory(slug);

        Gson specialGson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .registerTypeAdapter(Topic.class, new Category_TopicSerializer())
                .create();

        return new ResponseEntity<>(specialGson.toJson(topics), HttpStatus.OK);
    }

    @PostMapping("/topics")
    public ResponseEntity<String> addTopic(@RequestBody String payload, @RequestAttribute Map<String, Claim> userDetailsFromToken) {
        Gson specialGson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .registerTypeAdapter(Topic.class, new AddTopicSerializer())
                .create();

        Long userid = Long.parseLong(userDetailsFromToken.get("sub").asString());
        Map<String, String> data = gson.fromJson(payload, HashMap.class);
        String categorySlug = data.get("category");
        String content = data.get("content");
        String title = data.get("title");
        Topic topic = topicService.addTopic(categorySlug, content, title, userid);

        return new ResponseEntity<>(specialGson.toJson(topic), HttpStatus.OK);
    }
}
