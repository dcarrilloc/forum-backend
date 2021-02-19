package com.esliceu.forum.forum.services;

import com.esliceu.forum.forum.entities.Reply;

public interface ReplyService {
    Reply reply(String content, Long topic_id, Long userid);
    Reply updateReply(String content, Long reply_id);
    void deleteReply(Long topic_id, Long reply_id) throws Exception;
}
