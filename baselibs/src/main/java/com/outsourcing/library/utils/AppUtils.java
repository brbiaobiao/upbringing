package com.outsourcing.library.utils;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.outsourcing.library.application.BaseApplication;

import java.util.List;

import androidx.annotation.ColorRes;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;

public class AppUtils {
    public static Application getApp() {
        return (Application) BaseApplication.getIntansce();
    }
    
    public static int getColor(@ColorRes int colorId) {
        return ContextCompat.getColor(getApp(), colorId);
    }
    
    public static String getString(@StringRes int stringId) {
        return getApp().getString(stringId);
    }
    
    /**
     * 判断应用是否已经启动
     *
     * @param context     一个context
     * @param packageName 要判断应用的包名
     * @return boolean
     */
    private boolean isAppAlive(Context context, String packageName) {
        ActivityManager activityManager =
                (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> processInfos
                = activityManager.getRunningAppProcesses();
        for (int i = 0; i < processInfos.size(); i++) {
            if (processInfos.get(i).processName.equals(packageName)) {
                Log.i("NotificationLaunch",
                        String.format("the %s is running, isAppAlive return true", packageName));
                return true;
            }
        }
        Log.i("NotificationLaunch",
                String.format("the %s is not running, isAppAlive return false", packageName));
        return false;
    }
    
    /**
     * 判断MainActivity是否活动
     *
     * @param context      一个context
     * @param activityName 要判断Activity
     * @return boolean
     */
    private boolean isActivityAlive(Context context, String activityName) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(100);
        for (ActivityManager.RunningTaskInfo info : list) {
            if (info.topActivity.toString().equals(activityName) || info.baseActivity.toString().equals(activityName)) {
                Log.i("AppUtils", info.topActivity.getPackageName() + " info.baseActivity.getPackageName()=" + info.baseActivity.getPackageName());
                return true;
            }
        }
        return false;
    }
    
    /**
     * 检测某Activity是否在当前Task的栈顶
     */
    private boolean isTopActivity(Context context, String activityName) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = manager.getRunningTasks(1);
        String cmpNameTemp = null;
        if (list != null) {
            cmpNameTemp = list.get(0).topActivity.toString();
        }
        if (cmpNameTemp == null) {
            return false;
        }
        return cmpNameTemp.equals(activityName);
    }
    
    
    /**
     * 返回app运行状态
     *
     * @param packageName 要判断应用的包名
     * @return int 1:前台 2:后台 0:不存在
     */
    public static int checkAppState(Context context, String packageName) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> listInfos = activityManager
                .getRunningTasks(20);
        if (listInfos.get(0).topActivity.getPackageName().equals(packageName)) {
            return 1;
        } else {
            for (ActivityManager.RunningTaskInfo info : listInfos) {
                if (info.topActivity.getPackageName().equals(packageName)) {
                    return 2;
                }
            }
            return 0;
        }
    }
    
    /**
     * 判断进程是否运行
     * @param processName 应用程序的主进程名一般为包名
     */
    public static boolean isProessRunning(Context context, String processName) {
        boolean isRunning = false;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> lists = am.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo info : lists) {
            if (info.processName.equals(processName)) {
                isRunning = true;
            }
        }
        return isRunning;
    }

    /**
     * 获取应用程序名称
     */
    public static synchronized String getAppName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
