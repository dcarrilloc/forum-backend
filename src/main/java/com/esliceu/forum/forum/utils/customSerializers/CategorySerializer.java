package com.esliceu.forum.forum.utils.customSerializers;

import com.esliceu.forum.forum.entities.Category;
import com.esliceu.forum.forum.entities.User;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategorySerializer implements JsonSerializer<Category> {
    Gson gson = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .registerTypeAdapter(User.class, new UserSerializer())
            .create();

    @Override
    public JsonElement serialize(Category category, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonCategory = new JsonObject();
        jsonCategory.addProperty("color", category.getColor());
        jsonCategory.addProperty("description", category.getDescription());
        List<String> moderatorsIds = new ArrayList<>();
        Set<User> moderators = category.getModerators();
        if(moderators != null) {
            moderators.forEach(mod -> {
                moderatorsIds.add(String.valueOf(mod.getUser_id()));
            });
        }
        jsonCategory.add("moderators", gson.toJsonTree(moderatorsIds));
        jsonCategory.addProperty("slug", category.getSlug());
        jsonCategory.addProperty("title", category.getTitle());
        jsonCategory.addProperty("__v", 0);
        jsonCategory.addProperty("_id", String.valueOf(category.getCategory_id()));
        return jsonCategory;
    }
}
