package com.teaching.upbringing.net;

import android.text.TextUtils;
import android.widget.Toast;

import com.outsourcing.library.net.ExceptionHandler;
import com.outsourcing.library.utils.PreferenceManagers;
import com.teaching.upbringing.manager.AppManager;
import com.teaching.upbringing.manager.UserInfo;


/**
 * @author: biao
 * @create: 2019/4/9
 * @Describe: app异常处理类，拓展
 */
public class AppExceptionHandler extends ExceptionHandler {

    @Override
    protected RespondThrowable handleException(Throwable e) {
        if (e instanceof APIErrorException) {
            switch (((APIErrorException) e).code) {
                case 0://一般是某些参数缺少,其他不处理
                    if (e.getMessage().contains("身份过期")) {
                        //ResponseUtils.outLogin(e.getMessage());
                        return new RespondThrowable(e, ERROR.NOISE_ELIMINATION, "登录异常");
                    } else {
                        return new RespondThrowable(e, ERROR.QUEST_ERROR, e.getMessage());
                    }
                case 999:
                case 506:
                    ResponseUtils.toLogin();
                    PreferenceManagers.saveValue(UserInfo.USERID,"");
                    return new RespondThrowable(e, ERROR.DEFAULT_ERROR, e.getMessage());
                case 9999:
                    return new RespondThrowable(e, ERROR.DEFAULT_ERROR, e.getMessage());
                case 500:
                    return new RespondThrowable(e, ERROR.DEFAULT_ERROR, e.getMessage());
                case 501:
                    return new RespondThrowable(e, ERROR.DEFAULT_ERROR, e.getMessage());
                case 509:
                    return new RespondThrowable(e, ERROR.DEFAULT_ERROR, e.getMessage());
                case 1001:
                    return new RespondThrowable(e, ERROR.DEFAULT_ERROR, e.getMessage());
                case 130002:
                case 130003:
                    return new RespondThrowable(e, ERROR.DEFAULT_ERROR, e.getMessage());
                case 130004:
                    return new RespondThrowable(e, ERROR.DEFAULT_ERROR, e.getMessage());
                case -1:
                    //弹窗操作
                    return new RespondThrowable(e, 0, e.getMessage());
            }
        }
        return super.handleException(e);
    }

    @Override
    protected void showT(RespondThrowable e) {
        if (ERROR.NOISE_ELIMINATION == e.code) {
            return;
        }
        if (TextUtils.isEmpty(e.message)) {
            e.message = "未知异常";
        }
      /*  if (e.message.equals("未知异常")) {
            return;
        }*/
        Toast.makeText(AppManager.getApplication(), e.message, Toast.LENGTH_SHORT).show();
    }
}
