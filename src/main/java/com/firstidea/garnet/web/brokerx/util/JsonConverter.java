package com.firstidea.garnet.web.brokerx.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 *
 * @author gurjeet singh
 */
public final class JsonConverter {
    
    public static final String createJson(Object obj) {
        Gson gson = new Gson();
        String json = gson.toJson(obj);
        return json;
    }
    
    public final static Object fromJson(String json, Class<?> type) {
        Gson gson = new Gson();
        Object obj = gson.fromJson(json, type);
        return obj;
    }
    
    public final static Object parseJsonWithDateFormat(String json, Class<?> type, String dateFormat) {
        Gson gson = new GsonBuilder().setDateFormat(dateFormat).create();
        Object obj = gson.fromJson(json, type);
        return obj;
    }
}
