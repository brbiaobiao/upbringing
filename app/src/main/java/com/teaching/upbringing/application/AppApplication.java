package com.teaching.upbringing.application;


import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.outsourcing.library.application.BaseApplication;
import com.outsourcing.library.crash.AndroidCrash;
import com.outsourcing.library.crash.log.CrashListener;
import com.outsourcing.library.mvp.observer.ImplObserver;
import com.outsourcing.library.net.ExceptionHandler;
import com.outsourcing.library.net.retrofit.AppHttpClient;
import com.outsourcing.library.utils.AppUtils;
import com.outsourcing.library.utils.DateUtils;
import com.outsourcing.library.utils.LogUtils;
import com.outsourcing.library.utils.RxUtil;
import com.teaching.upbringing.manager.AppConfig;
import com.teaching.upbringing.manager.AppManager;
import com.teaching.upbringing.net.AppExceptionHandler;
import com.teaching.upbringing.net.AppRetrofitCreator;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.Date;

import de.mindpipe.android.logging.log4j.LogConfigurator;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author: biao
 * @create: 2019/4/8
 * @Describe:
 */
public class AppApplication extends BaseApplication{

    private static Handler mHandler;
    public static Activity mAppActivity;
    public LogConfigurator logConfigurator;
    public Logger logger = null;
    public static AppApplication mApp;

    @Override
    public void onCreate() {

        super.onCreate();
        init();
    }

    public static synchronized AppApplication getInstance() {
        if (mApp == null) {
            mApp = new AppApplication();
        }
        return mApp;
    }


    private void init() {
        mApp = this;
        AppManager.setApplication(this);
        mHandler = new Handler();
        Observable.empty()
                .observeOn(Schedulers.newThread())
                .subscribe(new ImplObserver<Object>(){
                    @Override
                    public void onComplete() {
                        super.onComplete();
                        initRxJavaErrorHandler();
                        if (AppConfig.IS_DEBUG) {
                            LogUtils.init(LogUtils.LEVEL_ALL);
                        } else {
                            LogUtils.init(LogUtils.LEVEL_WARN);
                        }

                        //初始化网络框架
                        AppHttpClient.init(new AppRetrofitCreator());
                    }
                });



    }

    private void initRxJavaErrorHandler(){
        ExceptionHandler.init(new AppExceptionHandler());
        RxUtil.setRxJavaErrorHandler();
    }

    //在mainActivity和loginActivity调用
    public void initConfig(){
        //初始化日志信息
        if(AppConfig.IS_DEBUG) {
            initLoginConfig();
        }
        //全局异常处理以及log记录
        AndroidCrash.getInstance().setLogFileDir(AppFileManager.getLogDir().getAbsolutePath())
                .setCrashReporter(myCrashListener).init(this, mHandler);
    }

    private void initLoginConfig() {
        //初始化日志设置
        this.logConfigurator = new LogConfigurator();
        //设置日志文件名称
        this.logConfigurator.setFileName(AppFileManager.getLogDir() + File.separator + DateUtils
                .date2String(new Date(), "yyyyMMdd") + ".log");
        this.logConfigurator.setFilePattern("%d [%p]-[%c.%M(%L)] %m %n");
        //设置日志级别
        this.logConfigurator.setRootLevel(Level.DEBUG);
        this.logConfigurator.setLevel("org.apache", Level.ERROR);
        //设置最大文件大小
        this.logConfigurator.setMaxFileSize(1024 * 1024 * 5);
        this.logConfigurator.setImmediateFlush(true);
        //应用日志设置
        this.logConfigurator.configure();
        //初始化日志
        this.logger = Logger.getLogger(AppApplication.class);
    }

    private CrashListener myCrashListener = new CrashListener() {
        @Override
        public void sendFile(File file, final Throwable ex) {

        }

        @Override
        public void closeApp(Thread thread, Throwable ex) {
            Toast.makeText(AppUtils.getApp(), "应用出现异常,即将退出...", Toast.LENGTH_LONG).show();
            if (logger != null) {
                logger.error("系统异常退出", ex);
            }
            new Handler(Looper.myLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    AppManager.AppExit(AppApplication.this);
                }
            }, 2000);
        }
    };

    public static AppApplication getApp() {
        return mApp;
    }

    public static Activity getCurrentActivity() {
        return mAppActivity;
    }

    public static void setApp(AppApplication app) {
        mApp = app;
    }



}
