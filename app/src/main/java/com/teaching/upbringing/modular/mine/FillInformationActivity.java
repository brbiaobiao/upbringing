package com.teaching.upbringing.modular.mine;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.outsourcing.library.utils.AppUtils;
import com.outsourcing.library.utils.KeyboardUtils;
import com.outsourcing.library.utils.ShapeUtils;
import com.teaching.upbringing.R;
import com.teaching.upbringing.mvpBase.BaseMVPActivity;

import butterknife.BindView;

/**
 * @author bb
 * @des ${修改昵称，简介，头衔，亮点}
 **/
public class FillInformationActivity extends BaseMVPActivity<FillInformationContract.Ipresenter> implements FillInformationContract.IView {

    public static final String SHOWTITLE = "show_title";
    public static final String UPDATATYPE = "updataType";
    private static final String HINTCONTENT = "hint_content";
    public static final String REBACKTEXT = "reback_text";

    @BindView(R.id.et_fill_info)
    EditText mEtFillInfo;
    private TextView titleRightText;

    public static Intent getCallIntent(Context context,String title,String hint,int updataType){
        Intent intent = new Intent(context, FillInformationActivity.class);
        intent.putExtra(SHOWTITLE,title);
        intent.putExtra(HINTCONTENT,hint);
        intent.putExtra(UPDATATYPE,updataType);
        return intent;
    }

    TextWatcher textWatcher = new TextWatcher(){
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            int length = mEtFillInfo.getText().toString().length();
            if(length!=0) {
                GradientDrawable shape = ShapeUtils.createShape(-1, 26, -1, null, "#FD8440");
                titleRightText.setBackground(shape);
            }
        }
    };

    @Override
    protected FillInformationContract.Ipresenter initPresenter() {
        return new FillInformationPresenter(this);
    }

    @Override
    protected Integer getContentId() {
        return R.layout.activity_fill_information;
    }

    @Override
    protected void init() {
        Intent intent = getIntent();
        String title = intent.getStringExtra(SHOWTITLE);
        String hint = intent.getStringExtra(HINTCONTENT);
        setTitleText(title);
        isShowTitleRightText(true);
        setTitleRightText("保存");
        setTitleRightTextColor(AppUtils.getColor(R.color.white));
        setTitleRightTextClick(v -> {
            getPresenter().upDataInfor(mEtFillInfo.getText().toString().trim());
            upDataCallBack();
        });
        titleRightText = getTitleRightText();
        GradientDrawable shape = ShapeUtils.createShape(-1, 26, -1, null, "#FEE1D2");
        titleRightText.setBackground(shape);

        mEtFillInfo.setHint(hint);
        mEtFillInfo.addTextChangedListener(textWatcher);

        getPresenter().getIntent(intent);
    }

    @Override
    public void upDataCallBack() {
        Intent intent1 = new Intent();
        intent1.putExtra(REBACKTEXT,mEtFillInfo.getText().toString().trim());
        setResult(RESULT_OK,intent1);
        KeyboardUtils.hideSoftInput(this);
        hideProgress();// TODO: 2019/11/3 后台改完接口就删掉
        finish();
    }
}
