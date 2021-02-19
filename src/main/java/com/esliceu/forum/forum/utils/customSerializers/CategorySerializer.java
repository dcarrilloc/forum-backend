package com.esliceu.forum.forum.utils.customSerializers;

import com.esliceu.forum.forum.entities.Category;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class CategorySerializer implements JsonSerializer<Category> {
    @Override
    public JsonElement serialize(Category category, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonCategory = new JsonObject();
        jsonCategory.addProperty("color", category.getColor());
        jsonCategory.addProperty("description", category.getDescription());
        //jsonCategory.add("moderators", category.getModerators());
        jsonCategory.addProperty("slug", category.getSlug());
        jsonCategory.addProperty("title", category.getTitle());
        jsonCategory.addProperty("__v", 0);
        jsonCategory.addProperty("_id", String.valueOf(category.getCategory_id()));
        return jsonCategory;
    }
}
