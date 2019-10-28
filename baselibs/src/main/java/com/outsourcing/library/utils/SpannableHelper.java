package com.outsourcing.library.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Parcel;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.TabStopSpan;
import android.text.style.TextAppearanceSpan;
import android.text.style.TypefaceSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.widget.TextView;

import androidx.annotation.ColorInt;

/**
 * @author bb
 * @time 2019/10/28 10:47
 * @des ${TODO}
 **/
public class SpannableHelper {

    private static Builder mBuilder;

    private SpannableHelper() {
    }

    /**
     * 开始方法
     *
     * @param str
     * @return
     */
    public static Builder start(String str) {
        if (mBuilder == null) {
            synchronized (SpannableHelper.class) {
                if (mBuilder == null) {
                    mBuilder = new Builder();
                }
            }
        }
        mBuilder.start(str);
        return mBuilder;
    }

    public static Builder newStart(String str) {
        Builder builder = new Builder();
        builder.start(str);
        return builder;
    }

    public static class Builder {

        private SpannableStringBuilder mStringBuilder;
        private int mLastAppendLength;//最后添加的字符长度
        private int mLastLength;//最后添加字符前的mStringBuilder长度
        private int mDefaultFlag = Spanned.SPAN_EXCLUSIVE_EXCLUSIVE;

        private StrikethroughSpan mStrikethroughSpan;
        private UnderlineSpan mUnderlineSpan;

        private Builder() {
            mStringBuilder = new SpannableStringBuilder();
        }

        /**
         * 开始
         *
         * @param str
         */
        private void start(String str) {
            mStringBuilder.clear();
            mLastLength = 0;
            mLastAppendLength = str.length();
            mStringBuilder.append(str);
        }

        public Builder next(String str) {
            mLastAppendLength = str.length();
            mLastLength = mStringBuilder.length();
            mStringBuilder.append(str);
            return this;
        }

        public Builder insertNextIfTrue(boolean is, String str) {
            if (is) {
                mLastAppendLength = str.length();
                mLastLength = mStringBuilder.length();
                mStringBuilder.append(str);
            }
            return this;
        }

        public void show2(TextView tv) {
            tv.setText(build());
        }

        public Builder nextLine(String str) {
            str = "\n" + str;
            return next(str);
        }

        /**
         * 完成
         *
         * @return
         */
        public SpannableStringBuilder build() {
            return mStringBuilder;
        }

        /**
         * 设置当前span模式
         *
         * @param flags Spanned.flags
         */
        public Builder setFlag(int flags) {
            mDefaultFlag = flags;
            return this;
        }

        public Builder size(int size) {
            AbsoluteSizeSpan span = new AbsoluteSizeSpan(size);
            return setSpan(span);
        }

        public Builder spSize(Context context, float spValue) {
            AbsoluteSizeSpan span = new AbsoluteSizeSpan(spToPx(context, spValue));
            return setSpan(span);
        }

        public Builder size(int size, boolean dip) {
            AbsoluteSizeSpan span = new AbsoluteSizeSpan(size, dip);
            return setSpan(span);
        }

        public Builder relativeSize(float proportion) {
            RelativeSizeSpan span = new RelativeSizeSpan(proportion);
            return setSpan(span);
        }

        public Builder color(@ColorInt int color) {
            ForegroundColorSpan span = new ForegroundColorSpan(color);
            mStringBuilder.setSpan(span, mLastLength, mStringBuilder.length(), mDefaultFlag);
            return setSpan(span);
        }

        public Builder backgroundColor(@ColorInt int color) {
            BackgroundColorSpan span = new BackgroundColorSpan(color);
            return setSpan(span);
        }

        /**
         * 设置字体style
         *
         * @param style Typeface.NORMAL
         *              Typeface.BOLD 粗体
         *              Typeface.ITALIC 斜体
         *              Typeface.BOLD_ITALIC 粗斜体
         */
        public Builder style(int style) {
            return setSpan(new StyleSpan(style));
        }

        /**
         * 删除线
         */
        public Builder strikethrough() {
            if (mStrikethroughSpan == null)
                mStrikethroughSpan = new StrikethroughSpan();
            return setSpan(mStrikethroughSpan);
        }

        /**
         * 删除线
         */
        public Builder deleteLine() {
            return strikethrough();
        }

        public Builder underline() {
            if (mUnderlineSpan == null)
                mUnderlineSpan = new UnderlineSpan();
            return setSpan(mUnderlineSpan);
        }

        /**
         * 使用这个必须要加入一些字符在前方,前方的字符可能会有一些问题
         */
        public Builder image(ImageSpan span) {
            return setSpan(span);
        }

        /**
         * 在单击链接时凡是有要执行的动作，都必须设置MovementMethod对象
         * textView.setMovementMethod(LinkMovementMethod.getInstance());
         * 设置点击后的颜色，这里涉及到ClickableSpan的点击背景
         * textView.setHighlightColor(0xff8FABCC);
         */
        public Builder url(String url) {
            return setSpan(new URLSpan(url));
        }

        /**
         * 在单击链接时凡是有要执行的动作，都必须设置MovementMethod对象
         * textView.setMovementMethod(LinkMovementMethod.getInstance());
         * 设置点击后的颜色，这里涉及到ClickableSpan的点击背景
         * textView.setHighlightColor(0xff8FABCC);
         */
        public Builder click(ClickableSpan span) {
            return setSpan(span);
        }

        /**
         * 设置文字字体。
         *
         * @param family "monospace"
         *               "serif"
         *               "sans-serif"
         */
        public Builder typeface(String family) {
            return setSpan(new TypefaceSpan(family));
        }

        /**
         * 设置文字字体、文字样式（粗体、斜体、等等）、文字颜色状态、文字下划线颜色状态等等。
         *
         * @param context
         * @param appearance
         */
        public void textAppearance(Context context, int appearance) {
            this.textAppearance(context, appearance, -1);
        }

        /**
         * 设置文字字体、文字样式（粗体、斜体、等等）、文字颜色状态、文字下划线颜色状态等等。
         *
         * @param context
         * @param appearance
         * @param colorList
         */
        public void textAppearance(Context context, int appearance, int colorList) {
            TextAppearanceSpan span = new TextAppearanceSpan(context, appearance, colorList);
            setSpan(span);
        }

        /**
         * 设置文字字体、文字样式（粗体、斜体、等等）、文字颜色状态、文字下划线颜色状态等等。
         *
         * @param family    "monospace"
         *                  "serif"
         *                  "sans-serif"
         * @param style     Typeface.NORMAL
         *                  Typeface.BOLD 粗体
         *                  Typeface.ITALIC 斜体
         *                  Typeface.BOLD_ITALIC 粗斜体
         * @param pxSize    单位px
         * @param color
         * @param linkColor
         */
        public void textAppearance(String family, int style, int pxSize, ColorStateList color, ColorStateList linkColor) {
            TextAppearanceSpan span = new TextAppearanceSpan(family, style, pxSize, color, linkColor);
            setSpan(span);
        }

        /**
         * 设置文字字体、文字样式（粗体、斜体、等等）、文字颜色状态、文字下划线颜色状态等等。
         *
         * @param family    "monospace"
         *                  "serif"
         *                  "sans-serif"
         * @param style     Typeface.NORMAL
         *                  Typeface.BOLD 粗体
         *                  Typeface.ITALIC 斜体
         *                  Typeface.BOLD_ITALIC 粗斜体
         * @param spSize    单位sp
         * @param color
         * @param linkColor
         */
        public void textAppearance(Context context, String family, int style, int spSize, ColorStateList color, ColorStateList linkColor) {
            TextAppearanceSpan span = new TextAppearanceSpan(family, style, spToPx(context, spSize), color, linkColor);
            setSpan(span);
        }

        /***
         * 每行的MarginLeft的偏移量（跟 \t 和 \n 有关系）
         * 等于marginLeft()
         * @see Builder#marginLeft(int)
         */
        public void tabStop(int where) {
            TabStopSpan.Standard standard = new TabStopSpan.Standard(where);
            setSpan(standard);
        }

        /**
         * 每行的MarginLeft的偏移量（跟 \t 和 \n 有关系）
         */
        public void marginLeft(int where) {
            TabStopSpan.Standard standard = new TabStopSpan.Standard(where);
            setSpan(standard);
        }

        /**
         * 上标
         * 具有next()效果
         *
         * @param str 要添加到后边的上标字符串
         */
        public void superscript(String str) {
            Parcel obtain = Parcel.obtain();
            obtain.writeString(str);
            SuperscriptSpan superscriptSpan = new SuperscriptSpan(obtain);
            next(str);
            setSpan(superscriptSpan);
            obtain.recycle();
        }

        //        *
        //         * 下标
        //
        //        public void subscript() {
        //        }
        //
        //        public void scaleX() {
        //        }
        //
        //        *
        //         * 设置文字左侧显示引用样式（一条竖线）
        //         * leftLine()
        //
        //        @Deprecated
        //        public void quote() {
        //        }
        //
        //        *
        //         * 设置文字左侧显示引用样式（一条竖线）
        //
        //        public void leftLine() {
        //        }


        /**
         * 将sp值转换为px值，保证文字大小不变
         *
         * @param spValue
         * @return
         */
        private int spToPx(Context context, float spValue) {
            float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
            return (int) (spValue * fontScale + 0.5f);
        }

        private Builder setSpan(Object span) {
            //            Log.v("bear","mLastLength:" + mLastLength + " mLastAppendLength:" + mLastAppendLength);
            mStringBuilder.setSpan(span, mLastLength, mStringBuilder.length(), mDefaultFlag);
            return this;
        }
    }
}
