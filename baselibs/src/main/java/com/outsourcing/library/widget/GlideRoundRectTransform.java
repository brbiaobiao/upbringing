package com.outsourcing.library.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.security.MessageDigest;

import androidx.annotation.NonNull;

public class GlideRoundRectTransform extends BitmapTransformation {

    public GlideRoundRectTransform(Context context) {
        super();
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        return roundRectCorp(pool, toTransform);
    }

    /**
     * 把图片剪切成圆角矩形
     * @param pool
     * @param source
     * @return
     */
    private Bitmap roundRectCorp(BitmapPool pool, Bitmap source) {
        if(source == null) {
            return null;
        }
        int width = source.getWidth();
        int height = source.getHeight();
        
        Bitmap result = pool.get(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(new BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        RectF roundRectBound = new RectF(0, 0, width, height);
        canvas.drawRoundRect(roundRectBound, 8,8, paint);
        return result;
    }
    
    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
    
    }
}
