package com.esliceu.forum.forum.utils.customSerializers;

import com.esliceu.forum.forum.entities.User;
import com.google.gson.*;
import org.springframework.core.io.FileSystemResource;

import java.lang.reflect.Type;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        Map<String, Object> permissions = new HashMap<>();

        List<String> root = new ArrayList<>();
        root.add("own_topics:write");
        root.add("own_topics:delete");
        root.add("own_replies:write");
        root.add("own_replies:delete");
        if(user.getRole().equals("admin")) {
            root.add("categories:write");
            root.add("categories:delete");
        }
        permissions.put("root", root);

        Map<String, Object> categories = new HashMap<>();
        if(user.getCategories_moderator().size() > 0) {
            user.getCategories_moderator().forEach(cm -> {
                List<String> category_slug = new ArrayList<>();
                category_slug.add("categories_topics:write");
                category_slug.add("categories_topics:delete");
                category_slug.add("categories_replies:write");
                category_slug.add("categories_replies:delete");
                categories.put(cm.getSlug(), category_slug);
            });
        }
        permissions.put("categories", categories);

        jsonUser.add("permissions", gson.toJsonTree(permissions));
        jsonUser.addProperty("__v", 0);
        jsonUser.addProperty("_id", String.valueOf(user.getUser_id()));
        return jsonUser;
    }
}
