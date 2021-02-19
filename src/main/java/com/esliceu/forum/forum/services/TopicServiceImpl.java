package com.esliceu.forum.forum.services;

import com.esliceu.forum.forum.entities.Category;
import com.esliceu.forum.forum.entities.Reply;
import com.esliceu.forum.forum.entities.Topic;
import com.esliceu.forum.forum.entities.User;
import com.esliceu.forum.forum.repos.CategoryRepo;
import com.esliceu.forum.forum.repos.TopicRepo;
import com.esliceu.forum.forum.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TopicServiceImpl implements TopicService {

    @Autowired
    CategoryRepo categoryRepo;

    @Autowired
    TopicRepo topicRepo;

    @Autowired
    UserRepo userRepo;

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

    @Override
    public Topic addTopic(String categorySlug, String content, String title, Long userid) {
        Optional<Category> optionalCategory = categoryRepo.findBySlug(categorySlug);
        Optional<User> optionalUser = userRepo.findById(userid);
        if(optionalCategory.isPresent() && optionalUser.isPresent()) {
            Category category = optionalCategory.get();

            Topic topic = new Topic();
            topic.setCategory(category);
            topic.setViews(0L);
            topic.setTitle(title);
            topic.setContent(content);
            topic.setCreatedAt(LocalDateTime.now());
            topic.setUpdatedAt(LocalDateTime.now());
            topic.setUser(optionalUser.get());

            return topicRepo.save(topic);
        }
        return null;
    }

    @Override
    public Topic updateTopic(Long id, String categorySlug, String content, String title) {
        Optional<Topic> optionalTopic = topicRepo.findById(id);
        Optional<Category> optionalCategory = categoryRepo.findBySlug(categorySlug);
        if(optionalTopic.isPresent() && optionalCategory.isPresent()) {
            Category category = optionalCategory.get();

            Topic topic = optionalTopic.get();
            topic.setTitle(title);
            topic.setContent(content);
            topic.setCategory(category);
            topic.setUpdatedAt(LocalDateTime.now());

            return topicRepo.save(topic);
        }
        return null;
    }

    @Override
    public Topic deleteTopic(Long id) {
        Optional<Topic> optionalTopic = topicRepo.findById(id);
        optionalTopic.ifPresent(topic -> topicRepo.delete(topic));
        return null;
    }
}
