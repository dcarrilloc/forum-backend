package com.esliceu.forum.forum.utils.customSerializers;

import com.esliceu.forum.forum.entities.Reply;
import com.esliceu.forum.forum.entities.User;
import com.google.gson.*;

import java.lang.reflect.Type;

public class ReplySerializer implements JsonSerializer<Reply> {
    Gson gson = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .registerTypeAdapter(User.class, new UserSerializer())
            .create();

    @Override
    public JsonElement serialize(Reply reply, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonReply = new JsonObject();
        jsonReply.addProperty("content", reply.getContent());
        jsonReply.addProperty("createdAt", reply.getCreatedAt().toString());
        jsonReply.addProperty("topic", String.valueOf(reply.getTopic().getTopic_id()));
        jsonReply.addProperty("updatedAt", reply.getUpdatedAt().toString());
        jsonReply.add("user", gson.toJsonTree(reply.getUser(), User.class));
        jsonReply.addProperty("__v", 0);
        jsonReply.addProperty("_id", String.valueOf(reply.getReply_id()));
        return jsonReply;
    }
}
