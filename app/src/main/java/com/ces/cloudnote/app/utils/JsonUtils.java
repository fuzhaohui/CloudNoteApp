package com.ces.cloudnote.app.utils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

/**
 * 有时候有的Gson会丢失类型信息，导致无法正常使用，目前还不知道是为什么
 * Created by remex on 14-7-16.
 */
public class JsonUtils<T> {

    public JsonUtils(){

    }

    public ArrayList<T> getListFromJson(String json, Class<T> clazz) {
        if(json == null){
            return null;
        }
        ArrayList<T> data = new ArrayList<T>();
        try {
            data = new Gson().fromJson(json, new TypeToken<ArrayList<T>>(){}.getType());
        }catch (JsonSyntaxException e){
            try{
                data.add(new Gson().fromJson(json, clazz));
            }catch (JsonSyntaxException e2) {
                LogUtils.e(e.toString());
            }
        }
        return data;
    }

    public String getJsonFromList(ArrayList<T> lists, Class<T> clazz){
        if(lists == null)
            return null;
        String data = "";
        try {
            data = new Gson().toJson(lists, new TypeToken<ArrayList<T>>(){}.getType());
        }catch (JsonSyntaxException e){
            try{
                data = new Gson().toJson(lists, clazz);
            }catch (JsonSyntaxException e2) {
                LogUtils.e(e.toString());
            }
        }
        return data;
    }

}
