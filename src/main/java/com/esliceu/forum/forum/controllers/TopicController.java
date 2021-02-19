package com.esliceu.forum.forum.controllers;

import com.esliceu.forum.forum.entities.Topic;
import com.esliceu.forum.forum.services.CategoryService;
import com.esliceu.forum.forum.services.TopicService;
import com.esliceu.forum.forum.utils.customSerializers.Category_TopicSerializer;
import com.esliceu.forum.forum.utils.customSerializers.TopicSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @GetMapping("/categories/{slug}/topics")
    public ResponseEntity<String> category_topics(@PathVariable String slug) {
        List<Topic> topics = topicService.getTopicFromCategory(slug);

        Gson specialGson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .registerTypeAdapter(Topic.class, new Category_TopicSerializer())
                .create();

        return new ResponseEntity<>(specialGson.toJson(topics), HttpStatus.OK);
    }
}
