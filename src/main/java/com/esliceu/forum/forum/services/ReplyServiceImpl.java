package com.esliceu.forum.forum.services;

import com.esliceu.forum.forum.entities.Reply;
import com.esliceu.forum.forum.entities.Topic;
import com.esliceu.forum.forum.entities.User;
import com.esliceu.forum.forum.repos.ReplyRepo;
import com.esliceu.forum.forum.repos.TopicRepo;
import com.esliceu.forum.forum.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ReplyServiceImpl implements ReplyService {
    @Autowired
    ReplyRepo replyRepo;

    @Autowired
    TopicRepo topicRepo;

    @Autowired
    UserRepo userRepo;

    @Override
    public Reply reply(String content, Long topic_id, Long userid) {
        Optional<Topic> optionalTopic = topicRepo.findById(topic_id);
        Optional<User> optionalUser = userRepo.findById(userid);
        if (optionalTopic.isPresent() && optionalUser.isPresent()) {
            Reply reply = new Reply();
            reply.setContent(content);
            reply.setCreatedAt(LocalDateTime.now());
            reply.setUpdatedAt(LocalDateTime.now());
            reply.setTopic(optionalTopic.get());
            reply.setUser(optionalUser.get());

            return replyRepo.save(reply);
        }
        return null;
    }

    @Override
    public Reply updateReply(String content, Long reply_id) {
        Optional<Reply> optionalReply = replyRepo.findById(reply_id);
        if(optionalReply.isPresent()) {
            Reply reply = optionalReply.get();
            reply.setContent(content);
            reply.setUpdatedAt(LocalDateTime.now());
            return replyRepo.save(reply);
        }
        return null;
    }

    @Override
    public void deleteReply(Long topic_id, Long reply_id) throws Exception {
        Optional<Reply> optionalReply = replyRepo.findById(reply_id);
        Optional<Topic> optionalTopic = topicRepo.findById(topic_id);
        if(optionalReply.isPresent() && optionalTopic.isPresent()) {
            Reply reply = optionalReply.get();
            replyRepo.delete(reply);
        } else {
            throw new Exception();
        }
    }
}
