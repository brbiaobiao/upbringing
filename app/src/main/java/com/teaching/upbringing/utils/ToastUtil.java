package com.teaching.upbringing.utils;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lefore.tutoring.R;
import com.outsourcing.library.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

import androidx.annotation.DrawableRes;

/**
 * use to:Toast工具,传入Activity作参数的会在Activity关闭时调用{@link #cancelToast(Activity)}取消,反之不会
 */
public class ToastUtil {
    public static final int SHORT = 300;
    public static final int LONG = 800;
    
    public static void showShort(String msg) {
        Toast.makeText(AppUtils.getApp(), msg, Toast.LENGTH_SHORT).show();
    }
    
    public static void showShort(Activity activity, String msg, int imgRes) {
        addToast(activity,createToast(activity, msg, imgRes, ToastUtil.SHORT)).show();
    }
    
    public static void showLong(String msg) {
        Toast.makeText(AppUtils.getApp(), msg, Toast.LENGTH_LONG).show();
    }
    
    public static void showLong(Activity activity, String msg, int imgRes) {
        addToast(activity,createToast(activity, msg, imgRes, ToastUtil.LONG)).show();
    }
    
    public static void showFault(CharSequence text) {
        createToast(AppUtils.getApp(), text, R.mipmap.icon_fail, SHORT).show();
    }
    
    public static void showFault(Activity context, CharSequence text) {
        addToast(context,createToast(context, text, R.mipmap.icon_fail, Toast.LENGTH_SHORT)).show();
    }
    
    public static void showFault(Activity context, CharSequence text, int duration) {
        addToast(context,createToast(context, text, R.mipmap.icon_fail, duration)).show();
    }
    
    public static void showSucceed(CharSequence text) {
        createToast(AppUtils.getApp(), text, R.mipmap.icon_success, SHORT).show();
    }
    
    public static void showSucceed(Activity context, CharSequence text) {
        addToast(context,createToast(context, text, R.mipmap.icon_success, ToastUtil.SHORT)).show();
    }
    
    public static void showSucceed(Activity context, CharSequence text, int duration) {
        addToast(context,createToast(context, text, R.mipmap.icon_success, duration)).show();
    }
    
    private static Toast createToast(Context context, CharSequence text, @DrawableRes int imgRes, int duration) {
        View v = LayoutInflater.from(context).inflate(R.layout.dialog_toast, null);
        TextView textView = (TextView) v.findViewById(R.id.textView_msg);
        ImageView img = (ImageView) v.findViewById(R.id.imageView_Icon);
        textView.setText(text);
        img.setImageResource(imgRes);
        Toast toast = new Toast(context);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(duration);
        toast.setView(v);
        return toast;
    }
    
    private static Toast addToast(Activity context, Toast toast) {
        List<Toast> toasts = Holder.mCacheMap.get(context);
        if (toasts == null) {
            toasts = new ArrayList<>(3);
            toasts.add(toast);
            Holder.mCacheMap.put(context, toasts);
        } else {
            toasts.add(toast);
        }
        return toast;
    }
    
    /**
     * 在BaseActivity内取消所有所属的Toast
     * @param context
     */
    public static void cancelToast(Activity context) {
        List<Toast> toasts = Holder.mCacheMap.get(context);
        if (toasts != null && !toasts.isEmpty()) {
            for (Toast toast : toasts) {
                if (toast != null) {
                    toast.cancel();
                }
            }
        }
    }
    
    private static class Holder {
        private static volatile WeakHashMap<Context, List<Toast>> mCacheMap = new WeakHashMap<>();
    }
}
