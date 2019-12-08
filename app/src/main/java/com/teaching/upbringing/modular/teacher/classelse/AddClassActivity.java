package com.teaching.upbringing.modular.teacher.classelse;

import android.content.Context;
import android.content.Intent;

import com.lefore.tutoring.R;
import com.outsourcing.library.utils.AppUtils;
import com.teaching.upbringing.mvpBase.BaseMVPActivity;

/**
 * @author ChenHh
 * @time 2019/12/8 18:25
 * @des
 **/
public class AddClassActivity extends BaseMVPActivity {

    public static void goInto(Context context) {
        Intent intent = new Intent(context, AddClassActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected Integer getContentId() {
        return R.layout.activity_add_class;
    }

    @Override
    protected void init() {
        setTitleText("新建课程");
        isShowTitleRightText(true);
        setTitleRightText("存草稿")
                .setTitleRightTextColor(AppUtils.getColor(R.color.color_9A9A9A))
                .setTitleRightTextClick(view -> {

                });
    }
}
