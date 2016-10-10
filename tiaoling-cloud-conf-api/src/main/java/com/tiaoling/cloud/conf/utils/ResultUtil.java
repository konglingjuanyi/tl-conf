package com.tiaoling.cloud.conf.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tiaoling.cloud.core.constant.ComErrorCodeConstants;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yhl on 2016/10/9.
 */
public class ResultUtil {
    public static String creObjSucResult(Object obj) {
        JSONObject resultMap = new JSONObject();
        resultMap.put("Code", 1);
        resultMap.put("Message", "请求成功");
        Gson gson = new GsonBuilder().serializeNulls().setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
        String json = gson.toJson(obj);
        JSONObject jsonObj = new JSONObject();
        jsonObj = JSONObject.fromObject(json);
        resultMap.put("Body", jsonObj);
        return resultMap.toString();
    }
    public static String creObjSucResult(Object obj,String message) {
        JSONObject resultMap = new JSONObject();
        resultMap.put("Code", 1);
        resultMap.put("Message", "请求成功");
        Gson gson = new GsonBuilder().serializeNulls().setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
        String json = gson.toJson(obj);
        JSONObject jsonObj = new JSONObject();
        jsonObj = JSONObject.fromObject(json);
        resultMap.put("Body", jsonObj);
        return resultMap.toString();
    }
    public static String creObjSucResultArray(Object obj) {
        JSONObject resultMap = new JSONObject();
        resultMap.put("Code", 1);
        resultMap.put("Message", "请求成功");
        Gson gson = new GsonBuilder().serializeNulls().setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
        String json = gson.toJson(obj);
        JSONArray jsonArray = new JSONArray();
        jsonArray = JSONArray.fromObject(json);
        resultMap.put("Body", jsonArray);
        return resultMap.toString();
    }
    public static Map<String, Object> creJsonArraySucResult(Object obj) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("IsSuccess", true);
        resultMap.put("Message",
                ComErrorCodeConstants.ErrorCode.SYSTEM_SUCCESS.getMemo());
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
        String json = gson.toJson(obj);
        JSONArray jsonArry = new JSONArray();
        jsonArry = JSONArray.fromObject(json);
        resultMap.put("Body", jsonArry);
        return resultMap;
    }

    /**
     * 返回空查询结果
     *
     * @return
     */
    public static String creComEmptyResult() {
        JSONObject resultMap = new JSONObject();
        resultMap.put("Code", 1);
        resultMap.put("Message", "操作成功");
        return resultMap.toString();
    }

    /**
     * 返回自定义结果
     *
     * @return
     */
    public static String creComSuccessResult(String message) {
        JSONObject resultMap = new JSONObject();
        resultMap.put("Code", 1);
        resultMap.put("Message", message);
        return resultMap.toString();
    }

    public static String creComErrorResult(String message) {
        JSONObject resultMap = new JSONObject();
        resultMap.put("Code", -1);
        resultMap.put("Message", message);
        return resultMap.toString();
    }
}
