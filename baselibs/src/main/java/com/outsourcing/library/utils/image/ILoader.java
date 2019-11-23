package com.outsourcing.library.utils.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import java.io.File;

import androidx.annotation.DrawableRes;

public interface ILoader {
    void load(Context context, String rsc, ImageLoader.Option options, ImageView view);
    
    void load(Context context, File rsc, ImageLoader.Option options, ImageView view);
    
    void load(Context context, @DrawableRes int rsc, ImageLoader.Option options, ImageView view);
    
    void load(Context context, Bitmap bitmap, ImageLoader.Option options, ImageView view);
    
    void load(Context context, Drawable drawable, ImageLoader.Option options, ImageView view);
}
