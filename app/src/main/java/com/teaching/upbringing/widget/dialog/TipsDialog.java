package com.teaching.upbringing.widget.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.lefore.tutoring.R;
import com.outsourcing.library.utils.AppUtils;
import com.outsourcing.library.utils.StringUtils;

import androidx.fragment.app.FragmentManager;
import butterknife.BindView;

/**
 * @author bb
 * @time 2019/10/25 13:48
 * @des ${TODO}
 **/
public class TipsDialog extends CommonDialog {
    @BindView(R.id.tv_content)
    TextView mTvContent;

    public TipsDialog(Context context, FragmentManager manager) {
        super(context, manager);
    }

    @Override
    protected View initView(Context context) {
        return LayoutInflater.from(context).inflate(R.layout.dialog_tips, null);
    }

    @Override
    protected void setMore(BaseDialog dialog) {
        getCoreDialog().isHideTitle(false);
        dialog.setConfirmColor(AppUtils.getColor(R.color.text_blue_0294f5));
        dialog.setCancelColor(AppUtils.getColor(R.color.text_blue_0294f5));
        setRightBtnText("确定");
        setLeftBtnText("取消");
    }


    public void setTips(String title, CharSequence content) {
        if (StringUtils.isEmpty(title)) {
            getCoreDialog().isHideTitle(true);
        } else {
            getCoreDialog().isHideTitle(false);
            getCoreDialog().setTitleText(title);
        }
        mTvContent.setText(content);
    }
}
