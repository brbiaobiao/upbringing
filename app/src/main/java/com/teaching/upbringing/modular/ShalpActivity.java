package com.teaching.upbringing.modular;

import com.lefore.tutoring.R;
import com.outsourcing.library.utils.PreferenceManagers;
import com.outsourcing.library.utils.StringUtils;
import com.teaching.upbringing.manager.UserInfo;
import com.teaching.upbringing.modular.main.MainActivity;
import com.teaching.upbringing.modular.user.LoginActivity;
import com.teaching.upbringing.mvpBase.BaseMVPActivity;

public class ShalpActivity extends BaseMVPActivity {

    public static final String ISFIRSTOPEN = "isFirstOpen";

    @Override
    protected Integer getContentId() {
        return R.layout.activity_shlap;
    }

    @Override
    protected void init() {
        String tokenId = PreferenceManagers.getString(UserInfo.USERID, "");
        if(StringUtils.isEmpty(tokenId)) {
            PreferenceManagers.saveValue(ISFIRSTOPEN,true);
            LoginActivity.goInto(this);
            finish();
        }else {
            PreferenceManagers.saveValue(ISFIRSTOPEN,false);
            MainActivity.goInto(this);
            finish();
        }
    }
}
