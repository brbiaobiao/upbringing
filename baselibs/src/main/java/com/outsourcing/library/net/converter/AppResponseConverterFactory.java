package com.outsourcing.library.net.converter;

import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.outsourcing.library.net.ExceptionHandler;
import com.outsourcing.library.net.RxHttpResponse;
import com.outsourcing.library.utils.AppUtils;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Retrofit;

/**
 * @author: biao
 * @create: 2019/4/8
 * @Describe: Gson转换器拓展
 */
public class AppResponseConverterFactory extends ResponseConverterFactory<AppResponseConverterFactory.AppGsonResponseBodyConverter<?>>{
    public AppResponseConverterFactory(Gson gson) {
        super(gson);
    }

    @Override
    protected AppGsonResponseBodyConverter<?> createResponseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        return new AppGsonResponseBodyConverter<>(gson,type);
    }

    public static class AppGsonResponseBodyConverter<T> extends CustomerGsonResponseBodyConverter<T> {

        public AppGsonResponseBodyConverter(Gson gson, Type type) {
            super(gson, type);
        }

        @Override
        public T convert(ResponseBody value) throws IOException {
            String response = value.string();
//            LogUtils.json(response);
            RxHttpResponse httpResult = gson.fromJson(response, RxHttpResponse.class);
            if (httpResult.getStatus().getCode() == 200) {
                return gson.fromJson(response, type);
            } else {
                throw new ExceptionHandler.APIErrorException(httpResult.getStatus().getMessage(), httpResult.getStatus().getCode());//自定义异常处理类
            }
        }
    }
}
