package com.teaching.upbringing.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.teaching.upbringing.modular.address.AddAddressActivity;
import com.teaching.upbringing.modular.address.CommonAddActivity;
import com.teaching.upbringing.modular.user.LoginActivity;

import java.util.WeakHashMap;

/**
 * @author bb
 * @time 2019/11/4 11:20
 * @des ${跳转同意管理类}
 **/
public class Navigation {

    private Context mContext;

    public Navigation(Context context) {
        mContext = context;
    }

    private static class Holder {
        private static volatile WeakHashMap<Context, Navigation> mCacheMap = new WeakHashMap<>();
    }

    public static Navigation getInstance(Context context) {
        Navigation navigation = Holder.mCacheMap.get(context);
        if (navigation == null) {
            navigation = new Navigation(context);
            Holder.mCacheMap.put(context, navigation);
        }
        return navigation;
    }

    public void startActivity(Intent intent) {
        startActivity(intent, 0);
    }

    public void startActivity(Intent intent, int requestCode) {
        if (mContext instanceof Activity) {
            ((Activity) mContext).startActivityForResult(intent, requestCode);
        } else if (mContext instanceof Application) {
            intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
        }
    }

    /**
     * 登陆页面
     */
    public void toLogin(){
        startActivity(LoginActivity.getCallInto(mContext));
    }

    /**
     * 常用地址页面
     */
    public void toCommonAdd(){
        startActivity(CommonAddActivity.goCallInto(mContext));
    }

    /**
     * 添加地址页面
     */
    public void toAddAddress(){
        startActivity(AddAddressActivity.getCallIntent(mContext));
    }

}
