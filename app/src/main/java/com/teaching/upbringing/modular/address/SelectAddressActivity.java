package com.teaching.upbringing.modular.address;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.widget.TextView;

import com.outsourcing.library.utils.AppUtils;
import com.outsourcing.library.utils.ShapeUtils;
import com.teaching.upbringing.R;
import com.teaching.upbringing.mvpBase.BaseMVPActivity;

/**
 * @author bb
 * @time 2019/11/7 17:20
 * @des ${TODO}
 **/
public class SelectAddressActivity extends BaseMVPActivity<SelectAddressContract.IPresenter> implements SelectAddressContract.IView {

    public static Intent goCallIntent(Context context){
        Intent intent = new Intent(context, SelectAddressActivity.class);
        return intent;
    }

    @Override
    protected Integer getContentId() {
        return null;
    }

    @Override
    protected void init() {
        setTitleText("添加地址");
        isShowTitleRightText(true);
        setTitleRightText("确认").setTitleRightTextColor(AppUtils.getColor(R.color.white))
                .setTitleRightTextClick(v -> {
                });
        TextView titleRightText = getTitleRightText();
        GradientDrawable shape = ShapeUtils.createShape(-1, 26, -1, null, "#FD8440");
        titleRightText.setBackground(shape);

    }
}
