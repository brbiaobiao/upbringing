package com.teaching.upbringing.modular.mine;

import android.content.Context;
import android.content.Intent;

import com.outsourcing.library.utils.StatusBarUtil;
import com.teaching.upbringing.R;
import com.teaching.upbringing.mvpBase.BaseMVPActivity;

public class AuthenticateActivity extends BaseMVPActivity {
    @Override
    protected Integer getContentId() {
        return R.layout.activity_authenticate;
    }

    @Override
    protected void init() {
        setTitleText("身份认证");
        StatusBarUtil.setStatusBarColor(this,R.color.white);
    }

    public static void goInto(Context context){
        Intent intent = new Intent(context, AuthenticateActivity.class);
        context.startActivity(intent);
    }

}
