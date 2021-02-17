package com.esliceu.forum.forum.services;

import com.esliceu.forum.forum.entities.Topic;

import java.util.List;

public interface TopicService {
    List<Topic> getTopicFromCategory(String slug);
    Topic getTopicById(Long id);
    void addView(Long id);
}
