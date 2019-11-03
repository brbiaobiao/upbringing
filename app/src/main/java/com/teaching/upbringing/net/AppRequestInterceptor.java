package com.teaching.upbringing.net;


import android.util.Log;

import com.outsourcing.library.utils.PreferenceManagers;
import com.outsourcing.library.utils.StringUtils;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @author: biao
 * @create: 2019/4/8
 * @Describe: 网络请求预处理拦截
 */
public class AppRequestInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request oldRequest = chain.request();
        Request.Builder builder = oldRequest.newBuilder();
        Request builder1 = builder.addHeader("x-access-token", PreferenceManagers.getString("tokenId", ""))
                .addHeader("Content-Type","application/json;charset=UTF-8").build();
        if (oldRequest.method().equals("POST")) {
            if (oldRequest.body() instanceof FormBody) {
                FormBody oldBody = (FormBody) oldRequest.body();
                oldRequest = getRequest(oldRequest, oldBody);
            } else {
                RequestBody body = oldRequest.body();
                if (body == null || body.contentLength() == 0) {
                    oldRequest = getRequest(oldRequest, null);
                }
            }
        }
        return chain.proceed(builder1);
    }

    private Request getRequest(Request oldRequest, FormBody oldBody) {
        FormBody.Builder bodyBuilder = new FormBody.Builder();
        //把原来的参数添加到新的构造器，（因为没找到直接添加，所以就new新的）
        if (oldBody != null) {
            for (int i = 0; i < oldBody.size(); i++) {
                bodyBuilder.addEncoded(oldBody.encodedName(i), oldBody.encodedValue(i));
            }
        }



        String header = oldRequest.header("x-access-token");
        if (!StringUtils.isEmpty(header)&&!"null".equals(header)) {
            // String header="eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJleHAiOjE1MTkzNzg5NTEsInVzZXJJZCI6OTQyNTk3ODg5MDY0OTY4MTkyLCJ1c2VybmFtZSI6ImxlZm9yZSIsInN1YiI6ImxlZm9yZSIsIndlY2hhdE9wZW5JZCI6IjEyMzQ1NiIsIndlY2hhdE5pY2tuYW1lIjoibGVmb3JlX3dlY2hhdCIsImFsaVVzZXJJZCI6IjEyMzQ1NiIsImFsaU5pY2tuYW1lIjoibGVmb3JlX2FsaSIsInBob25lIjoiMTg4MTk0NDY4NDUifQ.vNfQMTrs6RBUzPzxhgyZE15DxDGqx8f7iPD7_mNg5zzZT1HXmXiZAQbjKDfStX6-PLFywaKXD64k6j0qiQpr3xwbZeUT0QDF7VNA5eD_0jG347kkKjSwI3-uTkSNtBBkudeRMZmKas2VrE6hyZptmFTgJaSQbhj1m33UpzX9Djg";

            // SessionManager.setSession();
            Log.i("genericClients", header);
            PreferenceManagers.saveValue("tokenId", header);
        }
        //添加公共参数
        /*bodyBuilder
                .addEncoded("ver", AppConfig.dversion)
                .addEncoded("os", "android")
                .addEncoded("sign", Constants.LOGIN_SIGN);*/

        //用户信息
        /*if (UserInfo.isLogin()) {
            bodyBuilder.addEncoded("token", UserInfo.getAccount().getToken());
        }*/
        FormBody newBody = bodyBuilder.build();

        oldRequest = oldRequest.newBuilder()
                .post(newBody).build();
        return oldRequest;
    }




}
