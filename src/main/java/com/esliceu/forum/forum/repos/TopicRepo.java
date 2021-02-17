package com.esliceu.forum.forum.repos;

import com.esliceu.forum.forum.entities.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepo extends JpaRepository<Topic, Long> {

}
