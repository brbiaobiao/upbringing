package com.outsourcing.library.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.DisplayCutout;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowManager;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by SpannerBear on 2018/10/10.
 * use to:
 */
public class UIUtils {
    /**
     * 判断是否是全面屏
     */
    private volatile static boolean mIsAllScreenDevice;
    
    /**
     * 是否全面屏(推荐使用activity来检测)(使用Application的context在小米上更加准确)
     */
    public static boolean isAllScreenDevice(Context context) {
        if (context instanceof Activity) {
            return checkAllScreenInActivity((Activity) context) || checkAllScreenInApplication(context);
        } else {
            return checkAllScreenInApplication(context);
        }
    }
    
    public static boolean checkAllScreenInActivity(Activity context) {
        View decorView = context.getWindow().getDecorView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            WindowInsets windowInsets = decorView.getRootWindowInsets();
            if (windowInsets != null) {
                DisplayCutout displayCutout = windowInsets.getDisplayCutout();
                if (displayCutout != null) {
                    List<Rect> rects = displayCutout.getBoundingRects();
                    //通过判断是否存在rects来确定是否刘海屏手机
                    if (rects != null && rects.size() > 0) {
                        return false;
                    }
                }
            }
        }
        return false;
    }
    
    private static boolean checkAllScreenInApplication(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return false;
        }
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (windowManager != null) {
            Display display = windowManager.getDefaultDisplay();
            Point point = new Point();
            display.getRealSize(point);
            float width, height;
            if (point.x < point.y) {
                width = point.x;
                height = point.y;
            } else {
                width = point.y;
                height = point.x;
            }
            if (height / width >= 1.97f) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * 获取导航栏高度
     */
    public static int getVirtualBarHeight(Context context) {
        int vh = 0;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        try {
            @SuppressWarnings("rawtypes") Class c = Class.forName("android.view.Display");
            @SuppressWarnings("unchecked") Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, dm);
            vh = dm.heightPixels - display.getHeight();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vh;
    }
    
    
}