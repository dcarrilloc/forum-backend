package com.esliceu.forum.forum.repos;

import com.esliceu.forum.forum.entities.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepo extends JpaRepository<Reply, Long> {
}
