package com.teaching.upbringing.net;


import com.teaching.upbringing.utils.PreferenceManager;

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
        return chain.proceed(oldRequest);
    }

    private Request getRequest(Request oldRequest, FormBody oldBody) {
        FormBody.Builder bodyBuilder = new FormBody.Builder();
        //把原来的参数添加到新的构造器，（因为没找到直接添加，所以就new新的）
        if (oldBody != null) {
            for (int i = 0; i < oldBody.size(); i++) {
                bodyBuilder.addEncoded(oldBody.encodedName(i), oldBody.encodedValue(i));
            }
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
                .addHeader("x-access-token", PreferenceManager.getString("tokenId",""))
                .post(newBody).build();
        return oldRequest;
    }




}
