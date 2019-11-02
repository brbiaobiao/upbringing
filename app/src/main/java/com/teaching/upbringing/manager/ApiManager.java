package com.teaching.upbringing.manager;


import com.outsourcing.library.net.retrofit.AppHttpClient;
import com.teaching.upbringing.api.LoginApi;
import com.teaching.upbringing.api.MainApi;
import com.teaching.upbringing.api.RegisterApi;

/**
 * @author: biao
 * @create: 2019/4/10
 * @Describe:
 */
public class ApiManager {

    public static MainApi mainApi(){
        return AppHttpClient.create(MainApi.class);
    }

    public static LoginApi loginApi(){
        return AppHttpClient.create(LoginApi.class);
    }

    public static RegisterApi registerApi(){
        return AppHttpClient.create(RegisterApi.class);
    }
}
