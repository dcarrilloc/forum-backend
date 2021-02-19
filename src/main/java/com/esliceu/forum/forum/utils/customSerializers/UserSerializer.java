package com.esliceu.forum.forum.utils.customSerializers;

import com.esliceu.forum.forum.entities.User;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class UserSerializer implements JsonSerializer<User> {
    Gson gson = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create();

    @Override
    public JsonElement serialize(User user, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonUser = new JsonObject();
        jsonUser.addProperty("avatarUrl", user.getAvatar());
        jsonUser.addProperty("email", user.getEmail());
        jsonUser.addProperty("name", user.getName());
        jsonUser.addProperty("role", user.getRole());
        jsonUser.addProperty("id", String.valueOf(user.getUser_id()));
        jsonUser.add("permissions", gson.toJsonTree(new ArrayList<>()));
        jsonUser.addProperty("__v", 0);
        jsonUser.addProperty("_id", String.valueOf(user.getUser_id()));
        return jsonUser;
    }
}
