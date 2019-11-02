package com.teaching.upbringing.manager;


/**
 * @author: biao
 * @create: 2019/4/8
 * @Describe:api管理类
 */
public class ApiConfig {

    public static final String BASE_URL_PRODU_EVIN = "http://120.78.149.251/client-api/";
    public static final String BASE_URL_TEST_EVIN = "http://120.78.149.251/client-api/";

    public static String getBaseURL(){
        return AppConfig.IS_DEBUG?BASE_URL_TEST_EVIN:BASE_URL_PRODU_EVIN;
    }
}
