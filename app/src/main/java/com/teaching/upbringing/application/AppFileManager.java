package com.teaching.upbringing.application;

import android.graphics.Bitmap;

import com.outsourcing.library.utils.AppUtils;
import com.outsourcing.library.utils.FileUtils;
import com.outsourcing.library.utils.ImageUtils;
import com.outsourcing.library.utils.LogUtils;

import java.io.File;
import java.util.Calendar;

/**
 * @author ChenHh
 * @time 2019/10/24 15:32
 * @des ${TODO}
 **/
public class AppFileManager {

    /**
     * 程序使用的目录
     */
    private final static String DIR_APP = "com.teaching.upbringing";
    /**
     * oss断点续传缓存
     */
    private final static String DIR_OOS_RECORD = "oss_record";
    /**
     * 图片保存目录名
     */
    private final static String DIR_PICTURE = "upbringing";
    private final static String DIR_CHANNEL = "channel";

    public static File getPictureDir() {
        return FileUtils.getPictureDirectory(DIR_PICTURE);
    }

    public static File getSharePictureDir() {
        return getPictureDir();
    }

    public static File getVideoDir() {
        return FileUtils.getVideoDirectory(DIR_PICTURE);
    }

    public static File getOOSRecordDir() {
        String cacheDirectory = FileUtils.getCacheDirectory(AppUtils.getApp(),DIR_OOS_RECORD);
        return new File(cacheDirectory);
    }

    public static File getLogDir() {
        return FileUtils.getLogDirectory();
    }

    public static File getDownFileDir() {
        return FileUtils.getDownloadDirectory();
    }

    public static File getChannelDir() {
        return new File(AppUtils.getApp().getFilesDir(), DIR_CHANNEL);
    }

    public static String saveImage(Bitmap bmp) {
        return saveImage(bmp,System.currentTimeMillis() + ".jpg");
    }

    public static String saveImage(Bitmap bmp, String fileName) {
        return ImageUtils.saveImageToGallery(AppUtils.getApp(),bmp,AppFileManager.getPictureDir().getAbsolutePath(),fileName);
    }

    /**
     * 清理溢出文件
     */
    public static void clearOverFile() {
        File logDir = getLogDir();
        //删除5天前的数据
        File[] files = logDir.listFiles();
        Calendar instance = Calendar.getInstance();
        instance.set(Calendar.DAY_OF_YEAR,-5);
        long timeInMillis = instance.getTimeInMillis();
        for (File file : files) {
            if (file.lastModified() < timeInMillis) {
                LogUtils.v("file.lastModified:" + file.lastModified());
                file.delete();
            }
        }
    }
}
