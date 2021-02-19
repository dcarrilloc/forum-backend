package com.esliceu.forum.forum.services;

import com.esliceu.forum.forum.entities.Category;
import com.esliceu.forum.forum.entities.Reply;
import com.esliceu.forum.forum.entities.Topic;
import com.esliceu.forum.forum.repos.CategoryRepo;
import com.esliceu.forum.forum.repos.TopicRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TopicServiceImpl implements TopicService {

    @Autowired
    CategoryRepo categoryRepo;

    @Autowired
    TopicRepo topicRepo;

    @Override
    public List<Topic> getTopicFromCategory(String slug) {
        Optional<Category> optionalCategory = categoryRepo.findBySlug(slug);
        if (optionalCategory.isPresent()) {
            Category category = optionalCategory.get();
            return new ArrayList<>(category.getTopics());
        }
        return null;
    }

    @Override
    public Topic getTopicById(Long id) {
        Optional<Topic> optionalTopic = topicRepo.findById(id);
        if (optionalTopic.isPresent()) {
            return optionalTopic.get();
        }
        return null;
    }

    @Override
    public void addView(Long id) {
        Optional<Topic> optionalTopic = topicRepo.findById(id);
        if (optionalTopic.isPresent()) {
            Topic topic = optionalTopic.get();
            topic.setViews(topic.getViews() + 1);
            topicRepo.save(topic);
        }
    }

    @Override
    public List<Reply> getTopicReplies(long id) {
        Optional<Topic> optionalTopic = topicRepo.findById(id);
        return optionalTopic.map(topic -> new ArrayList<>(topic.getReplies())).orElseGet(ArrayList::new);
    }
}
