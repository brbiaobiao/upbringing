package com.teaching.upbringing.utils;



import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

/**
 * 获得屏幕相关的辅助类
 * @author zfgx
 *
 */
public class ScreenUtils
{
	private ScreenUtils()
	{
		/* cannot be instantiated */
		throw new UnsupportedOperationException("cannot be instantiated");
	}

	/**
	 * 获得屏幕宽度 如：800dp
	 * 
	 * @param context
	 * @return 屏幕高度 单位dp
	 */
	public static int getScreenWidth(Context context)
	{
		return getScreenDisplayMetrics(context).widthPixels;
	}

	/**
	 * 获得屏幕宽度,如：800dp
	 * 
	 * @param context
	 * @return 屏幕高度 单位dp
	 */
	public static int getScreenHeight(Context context)
	{
		return getScreenDisplayMetrics(context).heightPixels;
	}
	/**
	 * 获取屏幕宽度单位px,如：800px
	 * @param context
	 * @return
	 */
	public static int getScreenWidthPx(Context context){
		DisplayMetrics displayMetrics=getScreenDisplayMetrics(context);
		return  (int)(displayMetrics.widthPixels * displayMetrics.density + 0.5f);
	}

	/**
	 * 获取屏幕高度单位px,如：800px
	 * @param context
	 * @return
     */
	public static int getScreenHeightPx(Context context){
		DisplayMetrics displayMetrics=getScreenDisplayMetrics(context);
		return  (int)(displayMetrics.heightPixels * displayMetrics.density + 0.5f);
	}

	public static DisplayMetrics getScreenDisplayMetrics(Context context){
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		return outMetrics;
	}



	/**
	 * 获得状态栏的高度
	 * 
	 * @param context
	 * @return
	 */
	public static int getStatusHeight(Context context)
	{

		int statusHeight = -1;
		try
		{
			Class<?> clazz = Class.forName("com.android.internal.R$dimen");
			Object object = clazz.newInstance();
			int height = Integer.parseInt(clazz.getField("status_bar_height")
					.get(object).toString());
			statusHeight = context.getResources().getDimensionPixelSize(height);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return statusHeight;
	}

	/**
	 * 获取当前屏幕截图，包含状态栏
	 * 
	 * @param activity
	 * @return
	 */
	public static Bitmap snapShotWithStatusBar(Activity activity)
	{
		View view = activity.getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap bmp = view.getDrawingCache();
		int width = getScreenWidth(activity);
		int height = getScreenHeight(activity);
		Bitmap bp = null;
		bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
		view.destroyDrawingCache();
		return bp;

	}

	/**
	 * 获取当前屏幕截图，不包含状态栏
	 * 
	 * @param activity
	 * @return
	 */
	public static Bitmap snapShotWithoutStatusBar(Activity activity)
	{
		View view = activity.getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap bmp = view.getDrawingCache();
		Rect frame = new Rect();
		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		int statusBarHeight = frame.top;

		int width = getScreenWidth(activity);
		int height = getScreenHeight(activity);
		Bitmap bp = null;
		bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height
				- statusBarHeight);
		view.destroyDrawingCache();
		return bp;

	}

}
