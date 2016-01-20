package com.ces.cloudnote.app.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * 有时候有的Gson会丢失类型信息，导致无法正常使用，目前还不知道是为什么
 * Created by remex on 14-7-16.
 */
public class JsonUtils {

    private static JsonUtils instance;
    private static Gson gson;

    public JsonUtils() {
        gson = new GsonBuilder().create();
    }

    public static JsonUtils getInstance() {
        if(instance == null) {
            synchronized (JsonUtils.class) {
                if(instance == null) {
                    instance = new JsonUtils();
                }
            }
        }
        return instance;
    }

    public <T> String objectToJson(Class<T> clazz) {
        return gson.toJson(clazz);
    }

    public <T> ArrayList<T> fromJsonToList(String json, Type type) {
        return gson.fromJson(json, type);
    }

    public <T> T fromJson(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }
}
