package com.outsourcing.library.application;

import android.app.Activity;
import android.content.Context;

import androidx.multidex.MultiDexApplication;

/**
 * @author: biao
 * @create: 2019/4/8
 * @Describe:
 */
public class BaseApplication extends MultiDexApplication {

    protected static Context intansce;
    public static Activity mAppActivity;
    public static BaseApplication mApp;
    @Override
    public void onCreate() {
        super.onCreate();
        intansce = this;
        mApp = this;
    }

    public static Context getIntansce(){
        return intansce;
    }


    public static BaseApplication getApp() {
        return mApp;
    }

    public static Activity getCurrentActivity() {
        return mAppActivity;
    }

    public static void setApp(BaseApplication app) {
        mApp = app;
    }
}
