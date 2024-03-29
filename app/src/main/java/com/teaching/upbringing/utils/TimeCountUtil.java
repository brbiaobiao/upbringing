package com.teaching.upbringing.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.CountDownTimer;
import android.widget.TextView;

import com.lefore.tutoring.R;


/**
 * Created by Administrator on 2016/10/23.
 */

public class TimeCountUtil extends CountDownTimer {
    private Activity mActivity;
    private TextView btn;//按钮

    // 在这个构造方法里需要传入三个参数，一个是Activity，一个是总的时间millisInFuture，一个是countDownInterval，然后就是你在哪个按钮上做这个是，就把这个按钮传过来就可以了
    public TimeCountUtil(Activity mActivity, long millisInFuture, long countDownInterval, TextView btn) {
        super(millisInFuture, countDownInterval);
        this.mActivity = mActivity;
        this.btn =btn;
    }


    @SuppressLint("NewApi")
    @Override
    public void onTick(long millisUntilFinished) {
        btn.setClickable(false);//设置不能点击
        btn.setText("获取验证码"+millisUntilFinished / 1000 + "s");//设置倒计时时间

        btn.setTextColor(mActivity.getResources().getColor(R.color.color_9A9A9A));
        //设置按钮为灰色，这时是不能点击的
     //   btn.setBackground(mActivity.getResources().getDrawable(R.drawable.textview_shape_grad));
       // Spannable span = new SpannableString(btn.getText().toString());//获取按钮的文字
       // span.setSpan(new ForegroundColorSpan(mActivity.getResources().getColor(R.color.color_999999)), 0,5,Spannable.SPAN_INCLUSIVE_EXCLUSIVE);//讲倒计时时间显示为红色
       // btn.setText(span);

    }


    @SuppressLint("NewApi")
    @Override
    public void onFinish() {
        btn.setText("获取验证码");
        btn.setClickable(true);//重新获得点击
        btn.setTextColor(mActivity.getResources().getColor(R.color.color_0AA0FE));
     //   btn.setBackground(mActivity.getResources().getDrawable(R.drawable.textview_shape));//还原背景色

    }


}

