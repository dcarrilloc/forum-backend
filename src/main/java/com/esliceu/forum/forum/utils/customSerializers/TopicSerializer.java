package com.esliceu.forum.forum.utils.customSerializers;

import com.esliceu.forum.forum.entities.Category;
import com.esliceu.forum.forum.entities.Reply;
import com.esliceu.forum.forum.entities.Topic;
import com.esliceu.forum.forum.entities.User;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class TopicSerializer implements JsonSerializer<Topic> {
    Gson gson = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .registerTypeAdapter(User.class, new UserSerializer())
            .registerTypeAdapter(Category.class, new CategorySerializer())
            .registerTypeAdapter(Reply.class, new ReplySerializer())
            .create();

    @Override
    public JsonElement serialize(Topic topic, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonTopic = new JsonObject();

        jsonTopic.add("category", gson.toJsonTree(topic.getCategory(), Category.class));
        jsonTopic.addProperty("content", topic.getContent());
        jsonTopic.addProperty("createdAt", topic.getCreatedAt().toString());
        jsonTopic.addProperty("id", String.valueOf(topic.getTopic_id()));
        jsonTopic.addProperty("numberOfReplies", (Boolean) null);
        jsonTopic.add("replies", gson.toJsonTree(new ArrayList<>(topic.getReplies())));
        jsonTopic.addProperty("title", topic.getTitle());
        jsonTopic.addProperty("updatedAt", topic.getUpdatedAt().toString());
        jsonTopic.add("user", gson.toJsonTree(topic.getUser(), User.class));
        jsonTopic.addProperty("views", topic.getViews());
        jsonTopic.addProperty("__v", 0);
        jsonTopic.addProperty("_id", String.valueOf(topic.getTopic_id()));

        return jsonTopic;
    }
}
