package com.teaching.upbringing.widget.dialog;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.Context;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.outsourcing.library.utils.StringUtils;
import com.teaching.upbringing.R;

/**
 * @author ChenHh
 * @time 2019/10/24 16:19
 * @des ${TODO}
 **/
public class LoadingDialog extends Dialog {
    private Context mContext;

    TextView txt_msg;
    private final ImageView imageView_loadIcon;

    public LoadingDialog(Context context, String msg) {
        super(context, R.style.LoddingDialogStyle);
        this.mContext = context;
        setContentView(R.layout.dialog_progressbar);

        txt_msg = (TextView) findViewById(R.id.dialog_message_text);
        if (!StringUtils.isEmpty(msg))
            txt_msg.setText(msg);

        imageView_loadIcon = (ImageView) findViewById(R.id.imageView_LoadIcon);
        getWindow().setDimAmount(0);
        setCancelable(true);
        setCanceledOnTouchOutside(false);
    }

    /**
     * 设置文本提示信息
     *
     * @param msg
     */
    public void setMessageText(String msg) {
        txt_msg.setText(msg);
    }

    @Override
    public void show() {
        try {
            startAnim();
            super.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startAnim() {
        try {
            ObjectAnimator anim = ObjectAnimator
                    .ofFloat(imageView_loadIcon, "rotation", 0, 360);
            anim.setDuration(1100);
            anim.setRepeatCount(ValueAnimator.INFINITE);
            anim.setRepeatMode(ValueAnimator.RESTART);
            //设置差值器
            LinearInterpolator lin = new LinearInterpolator();
            anim.setInterpolator(lin);
            anim.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dismiss() {
        try {
            super.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
