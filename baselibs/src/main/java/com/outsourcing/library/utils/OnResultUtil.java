package com.outsourcing.library.utils;

import android.content.Intent;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import io.github.anotherjack.avoidonresult.ActivityResultInfo;
import io.github.anotherjack.avoidonresult.AvoidOnResult;
import io.reactivex.Observable;

import static android.app.Activity.RESULT_OK;

/**
 * use to:onResult回调。
 * 直接new,消耗不大
 */
public class OnResultUtil {
    
    private final AvoidOnResult mAvoid;
    
    public OnResultUtil(FragmentActivity act) {
        mAvoid = new AvoidOnResult(act);
    }
    
    public OnResultUtil(Fragment fragment) {
        mAvoid = new AvoidOnResult(fragment);
    }
    
    public static boolean isOk(ActivityResultInfo info) {
        return info.getResultCode() == RESULT_OK;
    }
    
    public Observable<ActivityResultInfo> call(Intent intent) {
        return mAvoid.startForResult(intent);
    }
}
