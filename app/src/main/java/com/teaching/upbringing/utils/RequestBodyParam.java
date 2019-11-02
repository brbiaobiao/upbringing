package com.teaching.upbringing.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class RequestBodyParam {
    private JSONObject pams;

    public RequestBodyParam() {
        pams = new JSONObject();
    }

    /**
     * 添加参数
     *
     * @param key
     * @param vlaue
     */
    public void addParam(String key, String vlaue) {
        try {
            pams.put(key, vlaue);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void addParam(String key, Integer vlaue) {
        try {
            pams.put(key, vlaue);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void addParam(String key, long vlaue) {
        try {
            pams.put(key, vlaue);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void addParam(String key, Double vlaue) {
        try {
            pams.put(key, vlaue);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void addParam(String key, Boolean vlaue) {
        try {
            pams.put(key, vlaue);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void addParam(String key, List vlaue) {

        JSONArray jsonArray=new JSONArray();

        for (int i = 0; i < vlaue.size(); i++) {
            jsonArray.put(vlaue.get(i));
        }

        try {
            pams.put(key, jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void addParam(String key, RequestBodyParamList vlaue) {
        try {
            pams.put(key, vlaue.getPams());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public Object getPams() {
        return pams;
    }
}
