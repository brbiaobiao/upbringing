package com.teaching.upbringing;

import android.Manifest;
import android.view.View;
import android.widget.Button;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.teaching.upbringing.modular.webview.WebViewActivity;
import com.teaching.upbringing.mvpBase.BaseMVPActivity;
import com.teaching.upbringing.utils.PhoneUtil;
import com.teaching.upbringing.widget.ConfigurableDialog;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseMVPActivity {

    @BindView(R.id.btn_webview)
    Button mBtnWebview;
    @BindView(R.id.btn_dialog)
    Button mBtndialog;
    @BindView(R.id.btn_my)
    Button mBtnMy;
    @BindView(R.id.btn_login)
    Button mBtnLogin;

    @Override
    protected Integer getContentId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
    }

    @OnClick({R.id.btn_webview, R.id.btn_dialog, R.id.btn_my, R.id.btn_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_webview:
                WebViewActivity.goInto(MainActivity.this,1,"https://www.baidu.com");
                break;
            case R.id.btn_dialog:
                new ConfigurableDialog(MainActivity.this)
                        .setTextModel(ConfigurableDialog.TWO_TXT)
                        .setTextFirst("审核不通过")
                        .setTextSecond("这里是不通过的原因，这里是不通过的原因，这里是不通过的原因，这里是不通过的原因，这里是不通过的原因，这里是不通过的原因，这里是不通过的原因，这里是不通过的原因")
                        .setBtnModel(ConfigurableDialog.TWO_BTN)
                        .setBtnName(new String[]{"确认","取消"})
                        .setBtnListener((ConfigurableDialog, view1) -> {
                            new RxPermissions(MainActivity.this)
                                    .request(Manifest.permission.CALL_PHONE)
                                    .filter(aBoolean -> aBoolean)
                                    .subscribe(aBoolean -> PhoneUtil.callP(MainActivity.this,"16602030443"));
                            ConfigurableDialog.dismiss();
                        })
                        .setBtnListener((ConfigurableDialog, view1) -> ConfigurableDialog.dismiss())
                        .show();
                break;
            case R.id.btn_my:

                break;
            case R.id.btn_login:

                break;
        }
    }

}
