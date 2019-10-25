package com.teaching.upbringing.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;

import com.outsourcing.library.utils.StringUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.teaching.upbringing.widget.ConfigurableDialog;

import androidx.fragment.app.FragmentActivity;

/**
 * @author bb
 * @time 2019/10/25 14:01
 * @des ${TODO}
 **/
public class PhoneUtil {
    @SuppressLint("CheckResult")
    public static void call(FragmentActivity context, String number) {
        new ConfigurableDialog(context)
                .setTextModel(ConfigurableDialog.TWO_TXT)
                .setTextFirst("拨打电话")
                .setTextSecond(number)
                .setBtnModel(ConfigurableDialog.TWO_BTN)
                .setBtnName(new String[]{"确认","取消"})
                .setBtnListener((ConfigurableDialog, view) -> {
                    new RxPermissions(context)
                            .request(Manifest.permission.CALL_PHONE)
                            .filter(aBoolean -> aBoolean)
                            .subscribe(aBoolean -> callP(context,number));
                    ConfigurableDialog.dismiss();
                })
                .setBtnListener((ConfigurableDialog, view) -> ConfigurableDialog.dismiss())
                .show();
    }
    public static void callP(FragmentActivity context, String number) {
        if (!StringUtils.isEmpty(number)) {
            Uri uri = Uri.parse("tel:" + number);
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_CALL);
            intent.setData(uri);
            context.startActivity(intent);
        }
    }
}
