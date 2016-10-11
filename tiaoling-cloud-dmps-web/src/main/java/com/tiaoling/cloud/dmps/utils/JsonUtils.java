package com.tiaoling.cloud.dmps.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.*;

/**
 * Created by yhl on 2016/10/9.
 */
public class JsonUtils {
    /**
     * 把数据对象转换成JSON字符串
     *
     * @param obj
     * @return
     */
    public static String getJSONString(Object obj) {
        return new Gson().toJson(obj);
    }



    /**
     * 从一个JSON对象字符格式中得到一个java对象
     *
     * @param Object
     * @param clazz
     * @return
     */
    public static <T> T getDTO(String jsonString, Class<T> clazz) {
        return new Gson().fromJson(new JsonParser().parse(jsonString), clazz);
    }

    public static Object getDTO(String jsonString, Type type) {
        return new Gson().fromJson(jsonString, type);
    }

    public static <T> T getJacksonDTO(String jsonString, Class<T> clazz) {
        try {
            return new ObjectMapper().readValue(jsonString, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 按照指定的日期格式进行转换
     *
     * @param jsonString
     * @param dateFormat
     * @param clazz
     * @return
     */
    public static <T> T getDTO(String jsonString, String dateFormat, Class<T> clazz) {
        Gson gson = new GsonBuilder().setDateFormat(dateFormat).create();

        return gson.fromJson(new JsonParser().parse(jsonString), clazz);
    }

    /**
     * json数组字符串转list
     *
     * @param <T>
     * @param jsonString
     * @param clazz
     * @return
     */
    public static <T> List<T> getListDTO(String jsonString, Class<T> clazz) {
        List<T> retList = new ArrayList<T>();
        Gson gson = new Gson();
        Type type = new TypeToken<List<T>>() {
        }.getType();
        List<T> tmpList = gson.fromJson(jsonString, type);
        for (int i = 0; i < tmpList.size(); i++) {
            String objStr = gson.toJson(tmpList.get(i), LinkedHashMap.class);
            T obj = gson.fromJson(objStr, clazz);
            retList.add(obj);
        }
        return retList;
    }

    /**
     * 根据json字符串返回Map对象
     *
     * @param json
     * @return
     */
    public static Map<String, Object> toMap(String json) {
        return toMap(parseJson(json));
    }

    /**
     * 获取JsonObject
     *
     * @param json
     * @return
     */
    public static JsonObject parseJson(String json) {
        JsonParser parser = new JsonParser();
        JsonObject jsonObj = parser.parse(json).getAsJsonObject();
        return jsonObj;
    }

    /**
     * 将JSONObjec对象转换成Map-List集合
     *
     * @param json
     * @return
     */
    public static Map<String, Object> toMap(JsonObject json) {
        Map<String, Object> map = new HashMap<String, Object>();
        Set<Map.Entry<String, JsonElement>> entrySet = json.entrySet();
        for (Iterator<Map.Entry<String, JsonElement>> iter = entrySet.iterator(); iter.hasNext();) {
            Map.Entry<String, JsonElement> entry = iter.next();
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof JsonArray)
                map.put((String) key, toList((JsonArray) value));
            else if (value instanceof JsonObject)
                map.put((String) key, toMap((JsonObject) value));
            else
                map.put((String) key, value);
        }
        return map;
    }

    /**
     * 将JSONArray对象转换成List集合
     *
     * @param json
     * @return
     */
    public static List<Object> toList(JsonArray json) {
        List<Object> list = new ArrayList<Object>();
        for (int i = 0; i < json.size(); i++) {
            Object value = json.get(i);
            if (value instanceof JsonArray) {
                list.add(toList((JsonArray) value));
            } else if (value instanceof JsonObject) {
                list.add(toMap((JsonObject) value));
            } else {
                list.add(value);
            }
        }
        return list;
    }

    /**
     * 给原json字符串添加新属性和值
     *
     * @param sourceJson
     * @return
     */
    public static String addProperty(String sourceJson,String pro,String val) {
        JsonObject jsonobj=parseJson(sourceJson);
        jsonobj.addProperty(pro, val);

        return jsonobj.toString();
    }
}
