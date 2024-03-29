package com.teaching.upbringing.modular.mine;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.lefore.tutoring.R;
import com.outsourcing.library.utils.AppUtils;
import com.outsourcing.library.utils.DensityUtils;
import com.outsourcing.library.utils.KeyboardUtils;
import com.outsourcing.library.utils.ShapeUtils;
import com.outsourcing.library.utils.StatusBarUtil;
import com.outsourcing.library.utils.StringUtils;
import com.teaching.upbringing.manager.UserInfo;
import com.teaching.upbringing.mvpBase.BaseMVPActivity;
import com.teaching.upbringing.utils.ToastUtil;

import butterknife.BindView;

/**
 * @author bb
 * @des ${修改昵称，简介，头衔，亮点}
 **/
public class FillInformationActivity extends BaseMVPActivity<FillInformationContract.Ipresenter> implements FillInformationContract.IView {

    public static final String SHOWTITLE = "show_title";
    public static final String UPDATATYPE = "updataType";
    public static final String INFO_TEXT = "info_text";
    private static final String HINTCONTENT = "hint_content";
    public static final String REBACKTEXT = "reback_text";

    @BindView(R.id.et_fill_info)
    EditText mEtFillInfo;
    @BindView(R.id.tv_num)
    TextView mTvNum;
    private TextView titleRightText;
    private int updateType;

    public static Intent getCallIntent(Context context,String title,String hint,int updataType,String info_text){
        Intent intent = new Intent(context, FillInformationActivity.class);
        intent.putExtra(SHOWTITLE,title);
        intent.putExtra(HINTCONTENT,hint);
        intent.putExtra(UPDATATYPE,updataType);
        intent.putExtra(INFO_TEXT,info_text);
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
            if(updateType==1){
                mTvNum.setText(length+"/"+"20");//昵称
            }else if(updateType==2){
                mTvNum.setText(length+"/"+"150");//简介
            }else if(updateType==3){
                mTvNum.setText(length+"/"+"15");//头衔
            }else if(updateType==4){
                mTvNum.setText(length+"/"+"30");//亮点
            }
            if(length!=0) {
                GradientDrawable shape = ShapeUtils.createShape(-1, 26, -1, null, "#CD2A2A");
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
        String info_text = intent.getStringExtra(INFO_TEXT);
        updateType = intent.getIntExtra(UPDATATYPE,0);
        if(updateType==1){
            mTvNum.setText(0+"/"+"20");//昵称
            mEtFillInfo.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});
        }else if(updateType==2){
            mTvNum.setText(0+"/"+"150");//简介
            mEtFillInfo.setFilters(new InputFilter[]{new InputFilter.LengthFilter(150)});
        }else if(updateType==3){
            mTvNum.setText(0+"/"+"15");//头衔
            mEtFillInfo.setFilters(new InputFilter[]{new InputFilter.LengthFilter(15)});
        }else if(updateType==4){
            mTvNum.setText(0+"/"+"30");//亮点
            mEtFillInfo.setFilters(new InputFilter[]{new InputFilter.LengthFilter(30)});
        }
        setTitleText(title);
        isShowTitleRightText(true);
        setTitleRightText("保存");
        setTitleRightTextColor(AppUtils.getColor(R.color.white));
        setTitleRightTextClick(v -> {
            getPresenter().upDataInfor(mEtFillInfo.getText().toString().trim()+"");
           // upDataCallBack();
        });
        titleRightText = getTitleRightText();
        GradientDrawable shape = ShapeUtils.createShape(-1, 36, -1, null, "#FD8440");
        titleRightText.setBackground(shape);
        titleRightText.setPadding(DensityUtils.dp2px(this,13f),
                DensityUtils.dp2px(this,3.5f),
                DensityUtils.dp2px(this,13f),
                DensityUtils.dp2px(this,3.5f));
        titleRightText.setBackground(shape);
        StatusBarUtil.setStatusBarColor(this,R.color.white);

        mEtFillInfo.addTextChangedListener(textWatcher);
        if(!StringUtils.isEmpty(info_text)) {
            mEtFillInfo.setText(info_text);
        }else {
            mEtFillInfo.setHint(hint);
        }
        getPresenter().getIntent(intent);
    }


    @Override
    public void upDataCallBack() {
        setResult(RESULT_OK);
        ToastUtil.showShort("编辑成功");
        KeyboardUtils.hideSoftInput(this);
        UserInfo.notifyUserInfoChange();
        finish();
    }

}
