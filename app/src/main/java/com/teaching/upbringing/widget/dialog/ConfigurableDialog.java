package com.teaching.upbringing.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.outsourcing.library.utils.StringUtils;
import com.teaching.upbringing.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author bb
 * @time 2019/10/25 13:56
 * @des 可配置dialog
 **/
public class ConfigurableDialog extends Dialog implements View.OnClickListener {
    private int mFirstGravity = Gravity.CENTER;
    private int mSecondGravity = Gravity.CENTER;

    private TextView textView_Cancel;
    private TextView textView_Confirm;
    private TextView textView_First;
    private TextView textView_Second;
    private TextView textView_Third;
    public static final int ONE_TXT = 0x00001111;//一层文本
    public static final int TWO_TXT = 0x00002222;//两层文本
    public static final int THREE_TXT = 0x00005555;//两层文本
    public static final int ONE_BTN = 0x00003333;//一个按钮
    public static final int TWO_BTN = 0x00004444;//两个按钮

    private String[] msgArray = new String[3];
    private String[] colorArray = new String[3];
    private int[] colorResArray = new int[2];

    private int txtModel = TWO_TXT;
    private int btnModel = TWO_BTN;
    private List<OnClickBtnListener> listenerList;
    private String[] btnName;
    private View rootView;
    private int width;

    public ConfigurableDialog(Context context) {
        super(context, R.style.UrgentDialog);

        DisplayMetrics dm = new DisplayMetrics();
        ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay()
                .getMetrics(dm);
        // 屏幕宽度（像素）
        width = dm.widthPixels;

        listenerList = new ArrayList<>();
        listenerList.clear();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去除标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_dialog);
        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.FILL_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setGravity(Gravity.CENTER);
        window.setAttributes(lp);
        textView_Cancel = findViewById(R.id.textView_Cancel);
        textView_Confirm = findViewById(R.id.textView_Confirm);
        textView_First = findViewById(R.id.textView_First);
        textView_Second = findViewById(R.id.textView_Second);
        textView_Third = findViewById(R.id.textView_Third);
        rootView = findViewById(R.id.rootView);
        /*rootView.post(() -> {
            ViewGroup.LayoutParams layoutParams = rootView.getLayoutParams();
            layoutParams.width = width;
            rootView.setLayoutParams(layoutParams);
        });*/
        setTextView();
        setBtnNum();
        setBtnName();
        textView_Confirm.setOnClickListener(this);
        textView_Cancel.setOnClickListener(this);
    }

    private void setBtnName() {
        if (btnName != null && btnName.length > 0) {
            switch (btnName.length) {
                case 1:
                    textView_Confirm.setText(btnName[0]);
                    break;
                case 2:
                    textView_Confirm.setText(btnName[0]);
                    textView_Cancel.setText(btnName[1]);
                    break;
            }
        }
    }

    private void setBtnNum() {
        if (textView_Cancel == null || textView_Confirm == null) return;
        switch (btnModel) {
            case ONE_BTN:
                textView_Cancel.setVisibility(View.GONE);
                textView_Confirm.setVisibility(View.VISIBLE);
                break;
            case TWO_BTN:
                textView_Cancel.setVisibility(View.VISIBLE);
                textView_Confirm.setVisibility(View.VISIBLE);
                break;
            default:
                textView_Cancel.setVisibility(View.VISIBLE);
                textView_Confirm.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void setTextView() {
        if (textView_First == null || textView_Second == null) return;
        switch (txtModel) {
            case ONE_TXT:
                textView_First.setGravity(mFirstGravity);
                textView_First.setVisibility(View.VISIBLE);
                textView_First.setText(msgArray[0]);
                //设置第一层文本颜色
                if (colorArray != null && !StringUtils.isBlank(colorArray[0])) {
                    textView_First.setTextColor(Color.parseColor(colorArray[0]));
                } else if (colorResArray != null && colorResArray[0] != 0) {
                    textView_First.setTextColor(getContext().getResources().getColor(colorResArray[0]));
                }
                textView_Second.setVisibility(View.GONE);
                textView_Third.setVisibility(View.GONE);
                break;
            case TWO_TXT:
                textView_First.setGravity(mFirstGravity);
                textView_First.setVisibility(View.VISIBLE);
                textView_First.setText(msgArray[0]);
                //设置第一层文本颜色
                if (colorArray != null && !StringUtils.isBlank(colorArray[0])) {
                    textView_First.setTextColor(Color.parseColor(colorArray[0]));
                } else if (colorResArray != null && colorResArray[0] != 0) {
                    textView_First.setTextColor(getContext().getResources().getColor(colorResArray[0]));
                }

                textView_Second.setGravity(mSecondGravity);
                textView_Second.setVisibility(View.VISIBLE);
                textView_Second.setText(msgArray[1]);
                //设置第二层文本颜色
                if (colorArray != null && !StringUtils.isBlank(colorArray[1])) {
                    textView_Second.setTextColor(Color.parseColor(colorArray[1]));
                } else if (colorResArray != null && colorResArray[1] != 0) {
                    textView_Second.setTextColor(getContext().getResources().getColor(colorResArray[1]));
                }
                textView_Third.setVisibility(View.GONE);
                break;
            case THREE_TXT:
                textView_First.setGravity(mFirstGravity);
                textView_First.setVisibility(View.VISIBLE);
                textView_First.setText(msgArray[0]);
                //设置第一层文本颜色
                if (colorArray != null && !StringUtils.isBlank(colorArray[0])) {
                    textView_First.setTextColor(Color.parseColor(colorArray[0]));
                } else if (colorResArray != null && colorResArray[0] != 0) {
                    textView_First.setTextColor(getContext().getResources().getColor(colorResArray[0]));
                }

                textView_Second.setGravity(mSecondGravity);
                textView_Second.setVisibility(View.VISIBLE);
                textView_Second.setText(msgArray[1]);
                //设置第二层文本颜色
                if (colorArray != null && !StringUtils.isBlank(colorArray[1])) {
                    textView_Second.setTextColor(Color.parseColor(colorArray[1]));
                } else if (colorResArray != null && colorResArray[1] != 0) {
                    textView_Second.setTextColor(getContext().getResources().getColor(colorResArray[1]));
                }

                textView_Third.setGravity(mSecondGravity);
                textView_Third.setVisibility(View.VISIBLE);
                textView_Third.setText(msgArray[2]);
                //设置第三层文本颜色
                if (colorArray != null && !StringUtils.isBlank(colorArray[2])) {
                    textView_Second.setTextColor(Color.parseColor(colorArray[2]));
                } else if (colorResArray != null && colorResArray[2] != 0) {
                    textView_Second.setTextColor(getContext().getResources().getColor(colorResArray[1]));
                }
                break;
            default:
                textView_First.setGravity(mFirstGravity);
                textView_First.setVisibility(View.VISIBLE);
                textView_First.setText(msgArray[0]);
                //设置第一层文本颜色
                if (colorArray != null && !StringUtils.isBlank(colorArray[0])) {
                    textView_First.setTextColor(Color.parseColor(colorArray[0]));
                } else if (colorResArray != null && colorResArray[0] != 0) {
                    textView_First.setTextColor(getContext().getResources().getColor(colorResArray[0]));
                }
                textView_Second.setGravity(mSecondGravity);
                textView_Second.setVisibility(View.VISIBLE);
                textView_Second.setText(msgArray[1]);
                //设置第二层文本颜色
                if (colorArray != null && !StringUtils.isBlank(colorArray[1])) {
                    textView_Second.setTextColor(Color.parseColor(colorArray[1]));
                } else if (colorResArray != null && colorResArray[1] != 0) {
                    textView_Second.setTextColor(getContext().getResources().getColor(colorResArray[1]));
                }
                break;
        }
    }

    /**
     * 设置第一层文本
     *
     * @param msg
     * @return
     */
    public ConfigurableDialog setTextFirst(String msg) {
        if (msgArray != null) {
            msgArray[0] = msg;
        }
        return this;
    }

    /**
     * 设置文本位置
     */
    public ConfigurableDialog setTextGravity(int gravity) {
        this.mFirstGravity = gravity;
        this.mSecondGravity = gravity;
        return this;
    }

    /**
     * 设置第一层文本位置
     */
    public ConfigurableDialog setFirstTextGravity(int gravity) {
        mFirstGravity = gravity;
        return this;
    }

    /**
     * 设置第二层文本位置
     */
    public ConfigurableDialog setSecondTextGravity(int gravity) {
        mSecondGravity = gravity;
        return this;
    }

    /**
     * 设置第一层文本
     *
     * @param msg
     * @param colorStr 颜色资源文件
     * @return
     */
    public ConfigurableDialog setTextFirst(String msg, String colorStr) {
        if (msgArray != null && colorArray != null) {
            msgArray[0] = msg;
            colorArray[0] = colorStr;
        }
        return this;
    }

    /**
     * 设置第一层文本
     *
     * @param msg
     * @param colorRes 颜色资源文件
     * @return
     */
    public ConfigurableDialog setTextFirst(String msg, int colorRes) {
        if (msgArray != null && colorArray != null) {
            msgArray[0] = msg;
            colorResArray[0] = colorRes;
        }
        return this;
    }

    /**
     * 设置第二层文本
     *
     * @param msg
     * @return
     */
    public ConfigurableDialog setTextSecond(String msg) {
        if (msgArray != null) {
            msgArray[1] = msg;
        }
        return this;
    }

    /**
     * 设置第二层文本
     *
     * @param msg
     * @param colorStr 第二层文本资源
     * @return
     */
    public ConfigurableDialog setTextSecond(String msg, String colorStr) {
        if (msgArray != null && colorArray != null) {
            msgArray[1] = msg;
            colorArray[1] = colorStr;
        }
        return this;
    }

    /**
     * 设置第三层文本
     *
     * @param msg
     * @param colorStr 第三层文本资源
     * @return
     */
    public ConfigurableDialog setTextThird(String msg, String colorStr) {
        if (msgArray != null && colorArray != null) {
            msgArray[2] = msg;
            colorArray[2] = colorStr;
        }
        return this;
    }

    /**
     * 设置第二层文本
     *
     * @param msg
     * @param colorRes 第二层文本资源
     * @return
     */
    public ConfigurableDialog setTextSecond(String msg, int colorRes) {
        if (msgArray != null && colorArray != null) {
            msgArray[1] = msg;
            colorResArray[1] = colorRes;
        }
        return this;
    }

    /**
     * 设置文本显示模式
     *
     * @param txtModel
     * @return
     */
    public ConfigurableDialog setTextModel(int txtModel) {
        this.txtModel = txtModel;
        return this;
    }

    /**
     * 设置按钮显示模式
     *
     * @param btnModel
     * @return
     */
    public ConfigurableDialog setBtnModel(int btnModel) {
        this.btnModel = btnModel;
        return this;
    }

    /**
     * 设置按钮监听，首先为确认监听，其次为取消监听
     *
     * @param listener
     * @return
     */
    public ConfigurableDialog setBtnListener(OnClickBtnListener listener) {
        if (listenerList != null) {
            listenerList.add(listener);
        }
        return this;
    }

    public ConfigurableDialog setBtnName(String[] btnName) {
        this.btnName = btnName;
        return this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textView_Confirm:
                if (listenerList != null) {
                    listenerList.get(0).onClick(this, textView_Confirm);
                }
                break;
            case R.id.textView_Cancel:
                if (listenerList != null) {
                    listenerList.get(1).onClick(this, textView_Cancel);
                }
                break;
        }
    }

    public interface OnClickBtnListener {
        void onClick(ConfigurableDialog ConfigurableDialog, View view);
    }

    @Override
    public void show() {
        setCancelable(false);
        super.show();
        setCanceledOnTouchOutside(false);
    }
}
