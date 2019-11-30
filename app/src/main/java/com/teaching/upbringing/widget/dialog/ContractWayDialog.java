package com.teaching.upbringing.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lefore.tutoring.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

/**
 * @author bb
 * @time 2019/11/5 17:09
 * @des ${联系dialog}
 **/
public class ContractWayDialog extends Dialog implements View.OnClickListener {

    private TextView tv_wx;
    private TextView tv_phone;
    private TextView tv_cancel;
    private RelativeLayout rl_call;
    private RelativeLayout rl_copy;

    private String[] msgArray = new String[2];
    private List<OnClickBtnListener> listeners;


    public ContractWayDialog(@NonNull Context context) {
        super(context, R.style.UrgentDialog);
        Window window = getWindow();
        // 此处可以设置dialog显示的位置为居中
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.FILL_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setGravity(Gravity.BOTTOM);
        window.setAttributes(lp);

        listeners = new ArrayList<>();
        listeners.clear();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_contract_way);
        tv_wx = findViewById(R.id.tv_wx);
        tv_phone = findViewById(R.id.tv_phone);
        tv_cancel = findViewById(R.id.tv_cancel);
        rl_call = findViewById(R.id.rl_call);
        rl_copy = findViewById(R.id.rl_copy);

        setTvText();

        tv_cancel.setOnClickListener(this);
        rl_call.setOnClickListener(this);
        rl_copy.setOnClickListener(this);
    }

    private void setTvText(){
        if(tv_wx == null || tv_phone == null) {
            return;
        }
        tv_wx.setText(msgArray[0]);
        tv_phone.setText(msgArray[1]);
    }

    /**
     * 设置微信号
     * @param msg
     * @return
     */
    public ContractWayDialog setTvWx(String msg){
        if(msgArray != null) {
            msgArray[0] = msg;
        }
        return this;
    }

    public ContractWayDialog setPhoneNum(String msg){
        if(msgArray != null) {
            msgArray[1] = msg;
        }
        return this;
    }

    /**
     * 设置点击监听，首先是复制，其次是拨打
     * @param listener
     * @return
     */
    public ContractWayDialog setBtnListener(OnClickBtnListener listener) {
        if (listeners != null) {
            listeners.add(listener);
        }
        return this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_call:
                if(listeners!=null) {
                    listeners.get(1).onClick(this,rl_call);
                }
                break;
            case R.id.rl_copy:
                if(listeners!=null) {
                    listeners.get(0).onClick(this,rl_copy);
                }
                break;
            case R.id.tv_cancel:
                dismiss();
                break;
        }
    }

    public interface OnClickBtnListener {
        void onClick(ContractWayDialog contractWayDialog, View view);
    }

    @Override
    public void show() {
        setCancelable(false);
        super.show();
        setCanceledOnTouchOutside(false);
    }
}
