package com.teaching.upbringing.modular.setting;

import android.content.Context;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

import com.outsourcing.library.utils.AppUtils;
import com.outsourcing.library.utils.StatusBarUtil;
import com.teaching.upbringing.BuildConfig;
import com.teaching.upbringing.R;
import com.teaching.upbringing.mvpBase.BaseMVPActivity;

import butterknife.BindView;

/**
 * @author bb
 * @time 2019/11/6 14:43
 * @des ${TODO}
 **/
public class AboutUsActivity extends BaseMVPActivity {
    @BindView(R.id.iv_app_logo)
    ImageView mIvAppLogo;
    @BindView(R.id.tv_app_name)
    TextView mTvAppName;
    @BindView(R.id.tv_version)
    TextView mTvVersion;

    public static void goInto(Context context){
        Intent intent = new Intent(context, AboutUsActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected Integer getContentId() {
        return R.layout.activity_about_us;
    }

    @Override
    protected void init() {
        setTitleText("关于我们");
        StatusBarUtil.setStatusBarColor(this, R.color.white);
        mTvVersion.setText("版本："+"v"+BuildConfig.VERSION_NAME);
        mTvAppName.setText(AppUtils.getAppName(this));
    }
}
