package com.teaching.upbringing.manager;


import com.outsourcing.library.net.retrofit.AppHttpClient;
import com.teaching.upbringing.api.MainApi;

/**
 * @author: biao
 * @create: 2019/4/10
 * @Describe:
 */
public class ApiManager {

    public static MainApi mainApi(){
        return AppHttpClient.create(MainApi.class);
    }
}
