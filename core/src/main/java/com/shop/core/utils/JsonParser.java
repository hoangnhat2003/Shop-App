package com.shop.core.utils;

import com.google.gson.Gson;
import lombok.NonNull;

public class JsonParser {

    public static String toJson(Object data) {

        Gson gson = new Gson();

        String json = gson.toJson(data);

        return json;
    }

    public static Object toObject(String json, @NonNull Class<?> className) {

        Gson gson = new Gson();

        Object data = gson.fromJson(json, className.getClass());

        return data;

    }
}
