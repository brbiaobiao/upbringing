package com.teaching.upbringing.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RequestBodyParamList {
    private JSONArray pamsList;

    public RequestBodyParamList() {
        pamsList = new JSONArray();
    }


    /**
     * 添加参数
     * @param vlaue
     */
    public void addParam(RequestBodyParam vlaue) {
        try {
            JSONObject pams = new JSONObject(vlaue.getPams().toString());

            pamsList.put(pams);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public Object getPams() {
        return pamsList;
    }
}
