package com.outsourcing.library.application;

import android.content.Context;

import androidx.multidex.MultiDexApplication;

/**
 * @author: biao
 * @create: 2019/4/8
 * @Describe:
 */
public class BaseApplication extends MultiDexApplication {

    protected static Context intansce;
    @Override
    public void onCreate() {
        super.onCreate();
        intansce = this;
    }

    public static Context getIntansce(){
        return intansce;
    }
}
