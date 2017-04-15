package com.yiru.fundamental.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import java.lang.reflect.Type;

/**
 * Created by Baowen on 2017/4/15.
 */
public class GsonUtils {
    private static final Gson gson = (new GsonBuilder()).setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    private static final Gson gsonWithSerializeNulls = (new GsonBuilder()).serializeNulls().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    private GsonUtils() {
    }

    public static String toJson(Object src) {
        return gson.toJson(src);
    }

    public static String toJsonWithSerializeNulls(Object src) {
        return gsonWithSerializeNulls.toJson(src);
    }

    public static <T> T fromJson(String jsonString, Class<T> classOfT) {
        return gson.fromJson(jsonString, classOfT);
    }

    public static <T> T fromJson(String jsonString, Type typeOfT) {
        return gson.fromJson(jsonString, typeOfT);
    }

    public static <T> T fromJson(JsonElement jsonElement, Type typeOfT) {
        return gson.fromJson(jsonElement, typeOfT);
    }

    public static String forLog(String string2Log) {
        if(string2Log == null) {
            return "null String";
        } else if(string2Log.length() > 10000) {
            int halfPrintLength = string2Log.length() / 10 > 4000?4000:string2Log.length() / 10;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string2Log.substring(0, halfPrintLength)).append("[...已省略...]").append(string2Log.substring(string2Log.length() - halfPrintLength));
            return stringBuilder.toString().replace("\r", "").replace("\n", "");
        } else {
            return string2Log.replace("\r", "").replace("\n", "");
        }
    }
}

