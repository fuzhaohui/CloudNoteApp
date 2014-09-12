package com.ces.cloudnote.app.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 自动构建HashMap的类，方便一些
 * Created by remex on 14-7-28.
 */
public class MapBuidler {

    private Map<String,String> params;

    public MapBuidler(){
        params = new HashMap<String, String>();
    }

    public MapBuidler put(String key, String value){
        params.put(key,value);
        return this;
    }

    public Map<String,String> create(){
        return params;
    }

}
