package com.esliceu.forum.forum.services;

import com.esliceu.forum.forum.entities.Reply;
import com.esliceu.forum.forum.entities.Topic;

import java.util.List;

public interface TopicService {
    List<Topic> getTopicFromCategory(String slug);
    Topic getTopicById(Long id);
    void addView(Long id);
    List<Reply> getTopicReplies(long topic_id);
    Topic addTopic(String categorySlug, String content, String title, Long userid);
    Topic updateTopic(Long id, String categorySlug, String content, String title);
    Topic deleteTopic(Long id);
}
