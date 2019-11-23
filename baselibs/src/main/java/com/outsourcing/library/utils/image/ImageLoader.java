package com.outsourcing.library.utils.image;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.TransitionOptions;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.outsourcing.library.R;
import com.outsourcing.library.mvp.observer.NextObserver;
import com.outsourcing.library.utils.LogUtils;
import com.outsourcing.library.widget.GlideRoundRectTransform;

import java.io.File;

import androidx.annotation.DrawableRes;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ImageLoader {
    
    private static volatile ILoader loader;
    
    private static ILoader getLoader() {
        if (loader == null) {
            synchronized (ImageLoader.class) {
                if (loader == null) {
                    loader = new GlideLoader();
                }
            }
        }
        return loader;
    }
    
    @SuppressLint("CheckResult")
    public static <Y extends Target<Bitmap>> void getBitmap(Context context, String url, Y target) {
        Glide.with(context)
                .asBitmap()
                .load(url)
                .into(target);
    }
    
    @SuppressLint("CheckResult")
    public static <Y extends Target<Drawable>> void getDrawable(Context context, String url, Y target) {
        Glide.with(context)
                .asDrawable()
                .load(url)
                .into(target);
    }
    
    public static void load(Context context, String url, ImageView view) {
        loadOption(context, url, Option.getDefaultOption(), view);
    }
    
    public static void load(Context context, File file, ImageView view) {
        loadOption(context, file, Option.getDefaultOption().version(file != null && file.exists() ? file.lastModified() : null), view);
    }
    
    public static void load(Context context, @DrawableRes int resImg, ImageView view) {
        loadOption(context, resImg, Option.getDefaultOption(), view);
    }
    
    public static void load(Context context, Bitmap bitmap, ImageView view) {
        loadOption(context, bitmap, Option.getDefaultOption(), view);
    }
    
    
    public static void load(Context context, String url, Option options, ImageView view) {
        loadOption(context, url, options, view);
    }
    
    public static void load(Context context, File file, Option options, ImageView view) {
        loadOption(context, file, options, view);
    }
    
    public static void load(Context context, @DrawableRes int resImg, @DrawableRes int placeHolder, ImageView view) {
        loadOption(context, resImg, Option.newOption(placeHolder), view);
    }
    
    
    public static void load(Context context, String url, @DrawableRes int placeHolder, ImageView view) {
        loadOption(context, url, Option.newOption(placeHolder), view);
    }
    
    /**
     * 无缓存加载图片
     */
    public static void loadSkipCache(Context context, String url, ImageView view) {
        getLoader().load(context, url, Option.getDefaultNoCacheOption(), view);
    }
    
    public static void loadSkipCache(Context context, File file, ImageView view) {
        getLoader().load(context, file, Option.getDefaultNoCacheOption(), view);
    }
    
    public static void loadSkipCache(Context context, @DrawableRes int resImg, ImageView view) {
        getLoader().load(context, resImg, Option.getDefaultNoCacheOption(), view);
    }
    
    public static void loadOption(Context context, String url, Option options, ImageView view) {
        getLoader().load(context, url, options, view);
    }
    
    public static void loadOption(Context context, File file, Option options, ImageView view) {
        getLoader().load(context, file, options, view);
    }
    
    public static void loadOption(Context context, @DrawableRes int resImg, Option options, ImageView view) {
        getLoader().load(context, resImg, options, view);
    }
    
    public static void loadOption(Context context, Bitmap bitmap, Option options, ImageView view) {
        getLoader().load(context, bitmap, options, view);
    }
    
    public static void loadImgRound(Context context, String url, ImageView view) {
        getLoader().load(context, url, Option.newOption().Round(context), view);
    }
    
    public static void loadImgRound(Context context, String url, Option options, ImageView view) {
        getLoader().load(context, url, options.Round(context), view);
    }
    
    public static void loadImgRound(Context context, String url, @DrawableRes int resImg, ImageView view) {
        getLoader().load(context, url, Option.newOption().Round(context).fallback(resImg).error(resImg).placeHolder(resImg), view);
    }
    
    
    public static void loadCircle(Context context, String url, @DrawableRes int resImg, ImageView view) {
        getLoader().load(context, url, Option.newOption(resImg).circel(), view);
    }
    
    public static void loadCircle(Context context, String url, ImageView view) {
        getLoader().load(context, url, Option.newOption().circel(), view);
    }
    
    public static void loadCircle(Context context, File file, ImageView view) {
        getLoader().load(context, file, Option.newOption().circel(), view);
    }
    
    
    public static void cleanAndLoad(Context context, String url, @DrawableRes int resImg, ImageView view) {
        Glide.with(context).clear(view);
        Glide.get(context).clearMemory();
        Observable.fromArray(1)
                .doOnNext(o -> {
                    LogUtils.v("清除缓存");
                    Glide.get(context).clearDiskCache();
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new NextObserver<Integer>() {
                    @Override
                    public void onNext(Integer integer) {
                        getLoader().load(context, url, Option.newOption(resImg).circel(), view);
                    }
                });
        
    }
    
    /**
     * 加载设置
     */
    public static class Option {
        public static final int TYPE_AS_NORMAL = -1;
        public static final int TYPE_AS_BITMAP = 0;
        public static final int TYPE_AS_DRAWABLE = 1;
        public static final int TYPE_AS_GIF = 2;
        /**
         * 禁止对两个默认实例进行动态设置修改
         */
        private static Option DEFAULT_OPTION = CreateDefaultOption();
        private static Option DEFAULT_OPTION_NO_CACHE = CreateDefaultNoCacheOption();
        
        RequestOptions mOptions;
        TransitionOptions transitionOptions;
        Object versionKey;
        int asType = -1;
        
        
        public Option() {
            mOptions = new RequestOptions();
        }
        
        public Option(RequestOptions options) {
            mOptions = options;
        }
        
        private static Option CreateDefaultOption() {
            return newOption();
        }
        
        private static Option CreateDefaultNoCacheOption() {
            return getNoCacheOption();
        }
        
        private static Option getDefaultOption() {
            if (DEFAULT_OPTION == null) {
                synchronized (Option.class) {
                    if (DEFAULT_OPTION == null) {
                        DEFAULT_OPTION = CreateDefaultOption();
                    }
                }
            }
            return DEFAULT_OPTION;
        }
        
        private static Option getDefaultNoCacheOption() {
            if (DEFAULT_OPTION_NO_CACHE == null) {
                synchronized (Option.class) {
                    if (DEFAULT_OPTION_NO_CACHE == null) {
                        DEFAULT_OPTION_NO_CACHE = CreateDefaultNoCacheOption();
                    }
                }
            }
            return DEFAULT_OPTION_NO_CACHE;
        }
        
        public static Option newOption() {
            return newOption(R.mipmap.icon_loading);
        }
        
        public static Option newOption(@DrawableRes int placeHolder) {
            Option option = new Option();
            option.mOptions = option.mOptions
                    .placeholder(placeHolder)
                    .error(placeHolder)
                    .fallback(placeHolder);
            return option;
        }
        
        public static Option getNoCacheOption() {
            return new Option(
                    new RequestOptions()
                            .placeholder(R.mipmap.icon_loading)
                            .error(R.mipmap.icon_loading)
                            .fallback(R.mipmap.icon_loading)
                            .skipMemoryCache(true)
                            .diskCacheStrategy(DiskCacheStrategy.NONE));
        }
        
        public static Option getNoCacheOption(@DrawableRes int placeHolder) {
            return new Option(
                    new RequestOptions()
                            .skipMemoryCache(true)
                            .placeholder(placeHolder)
                            .error(placeHolder)
                            .fallback(placeHolder)
                            .diskCacheStrategy(DiskCacheStrategy.NONE));
        }
        
        public static Option getNoCacheOption(Drawable placeHolder) {
            return new Option(
                    new RequestOptions()
                            .skipMemoryCache(true)
                            .placeholder(placeHolder)
                            .error(placeHolder)
                            .fallback(placeHolder)
                            .diskCacheStrategy(DiskCacheStrategy.NONE));
        }
        
        public Option placeHolder(Drawable drawable) {
            mOptions = mOptions.placeholder(drawable);
            return this;
        }
        
        public Option placeHolder(@DrawableRes int placeHolder) {
            mOptions = mOptions.placeholder(placeHolder);
            return this;
        }
        
        public Option error(Drawable drawable) {
            mOptions = mOptions.error(drawable);
            return this;
        }
        
        public Option error(@DrawableRes int placeHolder) {
            mOptions = mOptions.error(placeHolder);
            return this;
        }
        
        public Option fallback(Drawable drawable) {
            mOptions = mOptions.fallback(drawable);
            return this;
        }
        
        public Option fallback(@DrawableRes int placeHolder) {
            mOptions = mOptions.fallback(placeHolder);
            return this;
        }
        
        public Option fitCenter() {
            mOptions = mOptions.fitCenter();
            return this;
        }
        
        public Option centerCrop() {
            mOptions = mOptions.centerCrop();
            return this;
        }
        
        public Option Round(Context context) {
            mOptions = mOptions.transform(new GlideRoundRectTransform(context));
            return this;
        }
        
        public Option transition(TransitionOptions transitionOptions) {
            this.transitionOptions = transitionOptions;
            return this;
        }
        
       /* public Option transform(Transformation transformation) {
            this.transformation = transitionOptions;
            return this;
        }*/
        
        public Option circel() {
            mOptions = mOptions.bitmapTransform(new CircleCrop());
            return this;
        }
        
        /**
         * 淡出淡入
         *
         * @return
         */
        public Option crossFade() {
            return transition(new DrawableTransitionOptions().crossFade(200));
        }
        
        public Option crossFade(int duration) {
            return transition(new DrawableTransitionOptions().crossFade(duration));
        }
        
        public Option asBitMap() {
            this.asType = TYPE_AS_BITMAP;
            return this;
        }
        
        public Option asDrawable() {
            this.asType = TYPE_AS_BITMAP;
            return this;
        }
        
        /**
         * 设置缓存版本
         */
        public Option version(Object versionKey) {
            this.versionKey = versionKey;
            return this;
        }
    }
}
