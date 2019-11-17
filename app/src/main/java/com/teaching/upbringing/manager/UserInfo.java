package com.teaching.upbringing.manager;

import com.outsourcing.library.mvp.observer.NextObserver;
import com.outsourcing.library.utils.OnResultUtil;
import com.outsourcing.library.utils.PreferenceManagers;
import com.outsourcing.library.utils.StringUtils;
import com.teaching.upbringing.modular.user.LoginActivity;

import androidx.fragment.app.FragmentActivity;
import io.github.anotherjack.avoidonresult.ActivityResultInfo;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

/**
 * @author bb
 * @time 2019/11/4 17:14
 * @des ${用户信息管理类}
 **/
public class UserInfo {

    public static final String USERID = "userId";

    public static final String CITY_HISTORY = "city_history";

    /**
     * 判断是否登录
     * @return
     */
    public static boolean isLogin(){
        String userId = PreferenceManagers.getString("userId", "");
        return !StringUtils.isEmpty(userId);
    }

    private static BehaviorSubject<UserInfoChangeEvent> behaviorSubject = BehaviorSubject.create();

    /**
     * 发射用户信息改变事件
     */
    public static void notifyUserInfoChange(){
        behaviorSubject.onNext(new UserInfoChangeEvent());
    }

    public static Observable<UserInfoChangeEvent> getUserInfoChageOb(){
        return behaviorSubject;
    }

    /**
     * 用户信息改变事件
     */
    public static class UserInfoChangeEvent {
    }

    public static void toLoginIfUnLoginContinue(FragmentActivity context, NextObserver<ActivityResultInfo> observer) {
        if (isLogin()) {
            observer.onNext(null);
            return;
        }
        //2.8.0 从NewLoginActivity改为LoginRegisterMixActivity
        new OnResultUtil(context)
                .call(LoginActivity.getCallInto(context))
                .filter(OnResultUtil::isOk)
                .subscribe(observer);
    }
}
