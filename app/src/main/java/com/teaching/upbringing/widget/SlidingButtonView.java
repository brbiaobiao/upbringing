package com.teaching.upbringing.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

import com.lefore.tutoring.R;
import com.outsourcing.library.utils.DensityUtils;


public class SlidingButtonView extends HorizontalScrollView {
    private TextView mTextView_Delete;

    private int mScrollWidth;
    private Context mContext;
    private boolean isOpen;

    public SlidingButtonView(Context context) {
        this(context, null);
    }

    public SlidingButtonView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlidingButtonView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        this.setOverScrollMode(OVER_SCROLL_NEVER);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mTextView_Delete = findViewById(R.id.tvDelete_edittask_item);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) {
            this.scrollTo(0, 0);
            //获取水平滚动条可以滑动的范围，即右侧按钮的宽度
            //mScrollWidth = DensityUtils.dp2px(mContext, mTextView_Delete.getWidth());
            mScrollWidth = mTextView_Delete.getMeasuredWidth();
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                changeScrollx();
                return true;
            default:
                break;
        }
        return super.onTouchEvent(ev);
    }


    /**
     * 按滚动条被拖动距离判断关闭或打开菜单
     */
    public void changeScrollx() {
        int middleWidth = mScrollWidth / 2;
        int scrollWidth = getScrollX();

        if (scrollWidth >= middleWidth) {
            this.smoothScrollTo(DensityUtils.dp2px(mContext, mScrollWidth), 0);
        } else {
            this.smoothScrollTo(0, 0);
        }

    }

    /**
     * 隐藏删除按钮
     */
    public void hideDelete() {
        this.smoothScrollTo(0, 0);
    }
}
