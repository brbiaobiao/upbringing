package com.teaching.upbringing.net;

import android.content.Intent;
import android.text.TextUtils;
import android.widget.Toast;

import com.outsourcing.library.net.ExceptionHandler;
import com.teaching.upbringing.application.AppApplication;
import com.teaching.upbringing.manager.AppManager;
import com.teaching.upbringing.modular.user.LoginActivity;
import com.teaching.upbringing.utils.ToastUtil;


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
                    AppApplication.getApp().startActivity(new Intent(AppApplication.mAppActivity,LoginActivity.class));
                case 9999:
                    //ResponseUtils.outLogin(e.getMessage());
                    return new RespondThrowable(e, ERROR.NOISE_ELIMINATION, "登录异常");
                case 500:
                    ToastUtil.showShort("服务器异常");
                        return new RespondThrowable(e, ERROR.NOISE_ELIMINATION, "服务器异常");
                case 509:
                    ToastUtil.showShort("会话已失效，请退出应用后重新打开");
                    return new RespondThrowable(e, ERROR.NOISE_ELIMINATION, "会话已失效，请退出应用后重新打开");
                case 130002:
                    ToastUtil.showShort("用户手机号已存在");
                    return new RespondThrowable(e, ERROR.NOISE_ELIMINATION, "用户手机号已存在");
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
//            e.message = "未知异常";
        }
        if(e.message.equals("未知异常")) {
            return;
        }
        Toast.makeText(AppManager.getApplication(),e.message,Toast.LENGTH_SHORT).show();
    }
}
