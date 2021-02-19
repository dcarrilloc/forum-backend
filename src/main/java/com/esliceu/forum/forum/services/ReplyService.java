package com.esliceu.forum.forum.services;

import com.esliceu.forum.forum.entities.Reply;

public interface ReplyService {
    Reply reply(String content, Long topic_id, Long userid);
}
