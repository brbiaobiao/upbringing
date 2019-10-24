package com.outsourcing.library.crash;

import android.content.Context;
import android.os.Handler;

import com.outsourcing.library.crash.log.CrashCatcher;
import com.outsourcing.library.crash.log.CrashListener;

public class AndroidCrash {
    private static final AndroidCrash instance = new AndroidCrash();

    private CrashListener mReporter=null;

    private String mLogDir;

    private AndroidCrash(){}


    public static AndroidCrash getInstance() {
        return instance;
    }

    /**
     * 设置报告处理。
     * @param reporter
     * @return
     */
    public AndroidCrash setCrashReporter(CrashListener reporter) {
        mReporter = reporter;
        return this;
    }

    /**
     * 设置日志存储目录
     * @param logDir
     * @return
     */
    public AndroidCrash setLogFileDir(String logDir) {
        mLogDir = logDir;
        return this;
    }

    public void init(Context mContext, Handler handler) {
        if (mLogDir == null) {
            mLogDir =mContext.getFilesDir().getPath();
        }

        CrashCatcher.getInstance().init(mContext,mLogDir, mReporter,handler,Thread.getDefaultUncaughtExceptionHandler());
        Thread.setDefaultUncaughtExceptionHandler(CrashCatcher.getInstance());
    }


}