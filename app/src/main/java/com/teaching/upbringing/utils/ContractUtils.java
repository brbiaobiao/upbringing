package com.teaching.upbringing.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ClipboardManager;
import android.content.Context;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.teaching.upbringing.widget.dialog.ContractWayDialog;

import androidx.fragment.app.FragmentActivity;

public class ContractUtils {

    @SuppressLint("CheckResult")
    public static void UseContractDialog(FragmentActivity context, String wx, String phone){
        new ContractWayDialog(context)
                .setTvWx(wx)
                .setPhoneNum(phone)
                .setBtnListener((contractWayDialog, view1) -> {
                    // 从API11开始android推荐使用android.content.ClipboardManager
                    // 为了兼容低版本我们这里使用旧版的android.text.ClipboardManager，虽然提示deprecated，但不影响使用。
                    ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                    // 将文本内容放到系统剪贴板里。
                    cm.setText("12456789");
                    ToastUtil.showSucceed("复制成功");
                    contractWayDialog.dismiss();
                })
                .setBtnListener((contractWayDialog, view12) -> {
                    new RxPermissions(context)
                            .request(Manifest.permission.CALL_PHONE)
                            .filter(aBoolean -> aBoolean)
                            .subscribe(aBoolean -> {
                                PhoneUtil.callP(context,"19089989389");
                            });
                })
                .show();
    }
}
