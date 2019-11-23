package com.outsourcing.library.utils.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.signature.ObjectKey;
import com.outsourcing.library.mvp.observer.NextObserver;
import com.outsourcing.library.utils.AppUtils;
import com.outsourcing.library.utils.PreferenceManagers;

import java.io.File;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class GlideLoader implements ILoader {
    
    public GlideLoader() {
        clearTask();
    }
    
    /**
     * 缓存清理计划
     */
    private void clearTask() {
        Observable.just(1)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new NextObserver<Integer>() {
                    @Override
                    public void onNext(Integer integer) {
                        long start =PreferenceManagers.getLong(PreferenceManagers.LAST_GET_VERIFICATION_CODE_TIME,-1);
                        if (start <= 0) {//首次
                            PreferenceManagers.saveValue(PreferenceManagers.LAST_GET_VERIFICATION_CODE_TIME,System.currentTimeMillis());
                        } else {//非首次对比日期,
                            long currentTimeMillis = System.currentTimeMillis();
                            long between = (currentTimeMillis - start) / 1000;//除以1000是为了转换成秒
                            long day = between / (24 * 3600);
                            if (day >= 15) {//清除缓存
                                Glide.get(AppUtils.getApp()).clearDiskCache();
                            }
                        }
                    }
                });
    }
    
    //========================================加载基础方法start=================================================
    public void load(Context context, String rsc, ImageLoader.Option options, ImageView view) {
        RequestManager requestManager = handleOption(context, options);
        RequestBuilder<Drawable> load = requestManager.load(rsc);
        loadImage(options, view, load);
    }
    
    public void load(Context context, File rsc, ImageLoader.Option options, ImageView view) {
        RequestManager requestManager = handleOption(context, options);
        RequestBuilder<Drawable> load = requestManager.load(rsc);
        loadImage(options, view, load);
    }
    
    public void load(Context context, @DrawableRes int rsc, ImageLoader.Option options, ImageView view) {
        RequestManager requestManager = handleOption(context, options);
        RequestBuilder<Drawable> load = requestManager.load(rsc);
        loadImage(options, view, load);
    }
    
    @Override
    public void load(Context context, Bitmap bitmap, ImageLoader.Option options, ImageView view) {
        RequestManager requestManager = handleOption(context, options);
        RequestBuilder<Drawable> load = requestManager.load(bitmap);
        loadImage(options, view, load);
    }
    
    @Override
    public void load(Context context, Drawable drawable, ImageLoader.Option options, ImageView view) {
        RequestManager requestManager = handleOption(context, options);
        RequestBuilder<Drawable> load = requestManager.load(drawable);
        loadImage(options, view, load);
    }
    
    <TranscodeType, Y extends Target<TranscodeType>> void load(Context context, String rsc, ImageLoader.Option options, @NonNull Y target) {
        RequestManager requestManager = handleOption(context, options);
        RequestBuilder load = requestManager.load(rsc);
        if (options.transitionOptions != null) {
            load.transition(options.transitionOptions);
        }
        load.apply(options.mOptions)
                .into(target);
        
    }
    
    
    //========================================三个基础方法end=================================================
    
    /**
     * 对于option的转换处理
     *
     * @param context
     * @param options
     * @return
     */
    private static RequestManager handleOption(Context context, ImageLoader.Option options) {
        RequestManager with = Glide.with(context);
//        RequestManager with = Glide.with(context);
        if (ImageLoader.Option.TYPE_AS_NORMAL != options.asType) {
            switch (options.asType) {
                case ImageLoader.Option.TYPE_AS_BITMAP:
                    with.asBitmap();
                    break;
                case ImageLoader.Option.TYPE_AS_DRAWABLE:
                    with.asDrawable();
                    break;
                case ImageLoader.Option.TYPE_AS_GIF:
                    with.asGif();
                    break;
            }
        }
        return with;
    }
    
    private void loadImage(ImageLoader.Option options, ImageView view, RequestBuilder<Drawable> load) {
        if (options.transitionOptions != null) {
            load = load.transition(options.transitionOptions);
        }
        if (options.versionKey != null) {
            load = load.signature(new ObjectKey(options.versionKey));
        }
        load.apply(options.mOptions)
                .into(view);
    }
}
