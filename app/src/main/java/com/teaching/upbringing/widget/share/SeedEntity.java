package com.teaching.upbringing.widget.share;

import android.graphics.Bitmap;

/**
 * Created by SpannerBear on 2019/1/22.
 * use to:
 */
public class SeedEntity {
    public Bitmap mBitmap;
    public String imageFilePath;
    public String title;
    public String content;
    public String url;
    
    public SeedEntity(Bitmap image, String title, String content, String url) {
        this.mBitmap = image;
        this.title = title;
        this.content = content;
        this.url = url;
    }
    
    public SeedEntity(String imageFilePath, String title, String content, String url) {
        this.imageFilePath = imageFilePath;
        this.title = title;
        this.content = content;
        this.url = url;
    }
}
