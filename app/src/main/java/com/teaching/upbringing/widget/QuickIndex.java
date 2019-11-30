package com.teaching.upbringing.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.lefore.tutoring.R;
import com.outsourcing.library.utils.DensityUtils;


public class QuickIndex extends View {

	private static final String[] LETTERS = new String[]{
		"A", "B", "C", "D", "E", "F",
		"G", "H", "I", "J", "K", "L",
		"M", "N", "O", "P", "Q", "R",
		"S", "T", "U", "V", "W", "X",
		"Y", "Z"};
	private Paint paint;
	private float cellHeight;
	private int cellWidth;

	public QuickIndex(Context context) {
		this(context, null);
	}

	public QuickIndex(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public QuickIndex(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		
		paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(getResources().getColor(R.color.color_c3c3c));
		paint.setTextSize(DensityUtils.dp2px(getContext(),12));
//		paint.setTypeface(Typeface.DEFAULT_BOLD);// 字体加粗
	}

	@Override
	protected void onDraw(Canvas canvas) {
		for (int i = 0; i < LETTERS.length; i++) {
			String letter = LETTERS[i];
			// 求出每个位置的x, y 坐标, 绘制到界面
			
			// 获取指定文本/字符串宽度
			float measureText = paint.measureText(letter);
			// 计算x坐标
			float x = cellWidth * 0.5f - measureText * 0.5f; 
			
			Rect bounds = new Rect();
			// 将指定字符串的指定位置的矩形区域放置到bounds对象中.
			paint.getTextBounds(letter, 0, letter.length(), bounds);
			
			// 计算y坐标
			float y = cellHeight * 0.5f + bounds.height() * 0.5f + i * cellHeight;
			
			// 绘制字母A-Z
			canvas.drawText(letter, x, y, paint);
		}
	}
	
	int currentIndex = -1; // 当前选中的字母索引
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		int index = -1;
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				index = (int) (event.getY() / cellHeight); // [0, 25]
				
				// 如果在正确的范围内
				if(index >= 0 && index < LETTERS.length){
					// 跟刚刚的字母索引不同
					if(currentIndex != index){
						String letter = LETTERS[index];
						if(onLetterUpdateListener != null){
							onLetterUpdateListener.onLetterUpdate(letter);
						}
						currentIndex = index;// 更新记录的字母索引
					}
				}
				
				break;
			case MotionEvent.ACTION_MOVE:
				index = (int) (event.getY() / cellHeight); // [0, 25]
				
				// 如果在正确的范围内
				if(index >= 0 && index < LETTERS.length){
					// 跟刚刚的字母索引不同
					if(currentIndex != index){
						String letter = LETTERS[index];
						if(onLetterUpdateListener != null){
							onLetterUpdateListener.onLetterUpdate(letter);
						}
						currentIndex = index;// 更新记录的字母索引
					}
				}
				break;
			case MotionEvent.ACTION_UP:
				currentIndex = -1; // 清除记录的位置
				break;
	
			default:
				break;
		}
		
		return true;// 消费事件 
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		int mHeight = getMeasuredHeight();
		cellWidth = getMeasuredWidth();
		cellHeight = mHeight * 1.0f/ LETTERS.length;
		
	}
	
	private OnLetterUpdateListener onLetterUpdateListener;
	
	public OnLetterUpdateListener getOnLetterUpdateListener() {
		return onLetterUpdateListener;
	}
	public void setOnLetterUpdateListener(
			OnLetterUpdateListener onLetterUpdateListener) {
		this.onLetterUpdateListener = onLetterUpdateListener;
	}

	public interface OnLetterUpdateListener{
		
		void onLetterUpdate(String s);
		
	}
	
}
