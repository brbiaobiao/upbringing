package com.teaching.upbringing.manager;


import com.outsourcing.library.net.retrofit.AppHttpClient;
import com.teaching.upbringing.api.ForgetApi;
import com.teaching.upbringing.api.LoginApi;
import com.teaching.upbringing.api.MainApi;
import com.teaching.upbringing.api.PasswordLoginApi;
import com.teaching.upbringing.api.PersonInforApi;
import com.teaching.upbringing.api.RegisterApi;
import com.teaching.upbringing.api.SettingApi;

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

    public static PersonInforApi personInforApi(){return AppHttpClient.create(PersonInforApi.class);}

    public static SettingApi settingApi(){return AppHttpClient.create(SettingApi.class);}

    public static PasswordLoginApi passwordLoginApi(){
        return AppHttpClient.create(PasswordLoginApi.class);
    }

    public static ForgetApi forgetApi(){
        return AppHttpClient.create(ForgetApi.class);
    }


}
