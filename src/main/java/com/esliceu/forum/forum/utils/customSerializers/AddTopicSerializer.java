package com.esliceu.forum.forum.utils.customSerializers;

import com.esliceu.forum.forum.entities.Topic;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class AddTopicSerializer implements JsonSerializer<Topic> {
    @Override
    public JsonElement serialize(Topic topic, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonTopic = new JsonObject();

        jsonTopic.addProperty("category", String.valueOf(topic.getTopic_id()));
        jsonTopic.addProperty("content", topic.getContent());
        jsonTopic.addProperty("createdAt", topic.getCreatedAt().toString());
        jsonTopic.addProperty("id", String.valueOf(topic.getTopic_id()));
        jsonTopic.add("numberOfReplies", null);
        jsonTopic.add("replies", null);
        jsonTopic.addProperty("title", topic.getTitle());
        jsonTopic.addProperty("updatedAt", topic.getUpdatedAt().toString());
        jsonTopic.addProperty("user", String.valueOf(topic.getUser().getUser_id()));
        jsonTopic.addProperty("views", topic.getViews());
        jsonTopic.addProperty("__v", 0);
        jsonTopic.addProperty("_id", String.valueOf(topic.getTopic_id()));

        return jsonTopic;
    }
}
