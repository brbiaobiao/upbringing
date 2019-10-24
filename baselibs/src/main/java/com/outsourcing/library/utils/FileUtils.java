package com.outsourcing.library.utils;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import androidx.annotation.Nullable;

public class FileUtils {
    
    /**
     * (由于返回的目录有可能是外部无法访问的,调用此方法时要注意业务需要)
     * 获取应用专属缓存目录
     * android 4.4及以上系统不需要申请SD卡读写权限
     * 因此也不用考虑6.0系统动态申请SD卡读写权限问题，切随应用被卸载后自动清空 不会污染用户存储空间
     *
     * @param context 上下文
     * @param type    文件夹类型 可以为空，为空则返回API得到的一级目录
     * @return 缓存文件夹 如果没有SD卡或SD卡有问题则返回内存缓存目录，否则优先返回SD卡缓存目录
     */
    public static String getCacheDirectory(Context context, String type) {
        File appCacheDir = getExternalCacheDirectory(context, type);
        if (appCacheDir == null) {
            appCacheDir = getInternalCacheDirectory(context, type);
        }
        
        if (appCacheDir == null) {
            Log.e("FileUtils", "getCacheDirectory fail ,the reason is mobile phone unknown exception !");
        } else {
            if (!appCacheDir.exists() && !appCacheDir.mkdirs()) {
                Log.e("FileUtils", "getCacheDirectory fail ,the reason is make directory fail !");
            }
        }
        Log.d("FileUtil", "appCacheDir===" + appCacheDir.getPath() + File.separator);
        return appCacheDir.getPath() + File.separator;
    }
    
    /**
     * 获取SD卡缓存目录
     *
     * @param context 上下文
     * @param type    文件夹类型 如果为空则返回 /storage/emulated/0/Android/data/app_package_name/cache
     *                否则返回对应类型的文件夹如Environment.DIRECTORY_PICTURES 对应的文件夹为 .../data/app_package_name/files/Pictures
     *                {@link Environment#DIRECTORY_MUSIC},
     *                {@link Environment#DIRECTORY_PODCASTS},
     *                {@link Environment#DIRECTORY_RINGTONES},
     *                {@link Environment#DIRECTORY_ALARMS},
     *                {@link Environment#DIRECTORY_NOTIFICATIONS},
     *                {@link Environment#DIRECTORY_PICTURES}, or
     *                {@link Environment#DIRECTORY_MOVIES}.or 自定义文件夹名称
     * @return 缓存目录文件夹 或 null（无SD卡或SD卡挂载失败）
     */
    public static @Nullable
    File getExternalCacheDirectory(Context context, String type) {
        File appCacheDir = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            if (TextUtils.isEmpty(type)) {
                appCacheDir = context.getExternalCacheDir();
            } else {
                appCacheDir = context.getExternalFilesDir(type);
            }
            
            if (appCacheDir == null) {// 有些手机需要通过自定义目录
                appCacheDir = new File(Environment.getExternalStorageDirectory(), "Android/data/" + context.getPackageName() + "/cache/" + type);
            }
            
            if (appCacheDir == null) {
                Log.e("FileUtils", "getExternalDirectory fail ,the reason is sdCard unknown exception !");
            } else {
                if (!appCacheDir.exists() && !appCacheDir.mkdirs()) {
                    Log.e("FileUtils", "getExternalDirectory fail ,the reason is make directory fail !");
                }
            }
        } else {
            Log.e("FileUtils", "getExternalDirectory fail ,the reason is sdCard nonexistence or sdCard mount fail !");
        }
        return appCacheDir;
    }
    
    /**
     * 获取内存缓存目录
     *
     * @param type 子目录，可以为空，为空直接返回一级目录
     * @return 缓存目录文件夹 或 null（创建目录文件失败）
     * 注：该方法获取的目录是能供当前应用自己使用，外部应用没有读写权限，如 系统相机应用
     */
    public static File getInternalCacheDirectory(Context context, String type) {
        File appCacheDir = null;
        if (TextUtils.isEmpty(type)) {
            appCacheDir = context.getCacheDir();// /data/data/app_package_name/cache
        } else {
            appCacheDir = new File(context.getFilesDir(), type);// /data/data/app_package_name/files/type
        }
        
        if (!appCacheDir.exists() && !appCacheDir.mkdirs()) {
            Log.e("FileUtils", "getInternalDirectory fail ,the reason is make directory fail !");
        }
        return appCacheDir;
    }
    
    /**
     * 获取私有目录(外部程序无法访问)
     * 想要获取开放缓存目录的话查看{@link #getExternalCacheDirectory(Context, String)}
     *
     * @param dirName 自定义的文件夹名称(如果为空则使用cache文件夹)
     */
    /*public static File getPrivateCacheDirectory(String dirName) {
        File file = null;
        if (FileUtils.isSDCardEnable()) {
            file = AppUtils.getApp().getExternalFilesDir(dirName);
        }
        if (file == null) {
            file = new File(AppUtils.getApp().getFilesDir(), dirName);
        }
        if (!file.exists() && !file.mkdirs()) {
            Log.e("FileUtils", "getPrivateCacheDirectory fail ,the reason is make directory fail !");
        }
        return file;
    }*/
    
    
    /**
     * 获取图片文件夹(同类方法不抽取是因为对file对象创建的优化)
     *
     * @param dirName 自定义的文件夹名称(能够标识自己的app的)
     */
    public static File getPictureDirectory(String dirName) {
        File pictureDir = null;
        if (FileUtils.isSDCardEnable()) {
            pictureDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), dirName);
            if (!pictureDir.exists() && !pictureDir.mkdirs()) {
                pictureDir = new File(AppUtils.getApp().getExternalFilesDir(Environment.DIRECTORY_PICTURES), dirName);
            }
        }
        if (pictureDir == null) {
            pictureDir = new File(AppUtils.getApp().getFilesDir(), Environment.DIRECTORY_PICTURES);
        }
        if (!pictureDir.exists() && !pictureDir.mkdirs()) {
            Log.e("FileUtils", "getPictureDirectory fail ,the reason is make directory fail !");
        }
        return pictureDir;
    }
    
    public static File getVideoDirectory(String dirName) {
        File videoDir = null;
        if (FileUtils.isSDCardEnable()) {
            videoDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES), dirName);
            if (!videoDir.exists() && !videoDir.mkdirs()) {
                videoDir = new File(AppUtils.getApp().getExternalFilesDir(Environment.DIRECTORY_MOVIES), dirName);
            }
        }
        if (videoDir == null) {
            videoDir = new File(AppUtils.getApp().getFilesDir(), Environment.DIRECTORY_MOVIES);
        }
        if (!videoDir.exists() && !videoDir.mkdirs()) {
            Log.e("FileUtils", "getVideoDirectory fail ,the reason is make directory fail !");
        }
        return videoDir;
    }
    
    
    /**
     * 获取下载文件夹
     */
    public static File getDownloadDirectory() {
        File downloadFile = null;
        if (FileUtils.isSDCardEnable()) {
            downloadFile = AppUtils.getApp().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
        }
        if (downloadFile == null) {
            downloadFile = new File(AppUtils.getApp().getFilesDir(), Environment.DIRECTORY_DOWNLOADS);
        }
        if (!downloadFile.exists() && !downloadFile.mkdirs()) {
            Log.e("FileUtils", "getDownloadDirectory fail ,the reason is make directory fail !");
        }
        return downloadFile;
    }
    
    /**
     * 获取app的log文件夹
     */
    public static File getLogDirectory() {
        File logFile = null;
        if (FileUtils.isSDCardEnable()) {
            logFile = new File(AppUtils.getApp().getExternalFilesDir(null), "Log");
        }
        if (logFile == null) {
            logFile = new File(AppUtils.getApp().getFilesDir(), "Log");
        }
        if (!logFile.exists() && !logFile.mkdirs()) {
            Log.e("FileUtils", "getLogDirectory fail ,the reason is make directory fail !");
        }
        return logFile;
    }
    
    
    /**
     * 删除缓存文件
     *
     * @param context
     * @param fileName 文件名称
     */
    public static void clearCacheFile(Context context, String fileName) {
        String filepath = getCacheDirectory(context, "");
        File file = new File(filepath + fileName);
        if (file.exists()) {
            file.delete();
        }
        
    }
    
    /**
     * 保存文件
     */
    public static boolean saveFile(byte[] data, String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            boolean delete = file.delete();
            if (!delete) {
                LogUtils.v("file存在,删除失败");
                return false;
            }
        }
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
        BufferedInputStream inputStream = new BufferedInputStream(byteArrayInputStream);
        FileOutputStream fileOutputStream = null;
        if (!file.getParentFile().exists()) {
            boolean mkdir = file.getParentFile().mkdirs();
            if (!mkdir) {
                LogUtils.v("创建文件夹失败");
                return false;
            }
        }
        
        try {
            fileOutputStream = new FileOutputStream(file);
            BufferedOutputStream outputStream = new BufferedOutputStream(fileOutputStream);
            byte[] bytes = new byte[1024];
            while (inputStream.read(bytes) != -1) {
                outputStream.write(bytes);
            }
            outputStream.flush();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * 得到SD卡根目录.
     */
    public static File getRootPath() {
        File path = null;
        if (sdCardIsAvailable()) {
            path = Environment.getExternalStorageDirectory(); // 取得sdcard文件路径
        } else {
            path = Environment.getDataDirectory();
        }
        return path;
    }
    
    /**
     * 获取的目录默认没有最后的”/”,需要自己加上
     * 获取本应用图片缓存目录
     *
     * @return
     */
    public static File getCacheFolder(Context context) {
        File folder = new File(context.getCacheDir(), "IMAGECACHE");
        if (!folder.exists()) {
            folder.mkdir();
        }
        return folder;
    }
    
    /**
     * 判断SD卡是否可用
     *
     * @return true : 可用<br>false : 不可用
     */
    public static boolean isSDCardEnable() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }
    
    /**
     * 获取SD卡路径
     * <p>一般是/storage/emulated/0/</p>
     *
     * @return SD卡路径
     */
    public static String getSDCardPath() {
        if (!isSDCardEnable()) {
            return "";
        }
        return Environment.getExternalStorageDirectory().getPath() + File.separator;
    }
    
    /**
     * 获取SD卡Data路径
     *
     * @return SD卡Data路径
     */
    public static String getDataPath() {
        if (!isSDCardEnable()) {
            return "";
        }
        return Environment.getDataDirectory().getPath();
    }
    
    
    /**
     * SD卡是否可用.
     */
    public static boolean sdCardIsAvailable() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File sd = new File(Environment.getExternalStorageDirectory().getPath());
            return sd.canWrite();
        } else {
            return false;
        }
    }
    
    /**
     * 文件或者文件夹是否存在.
     */
    public static boolean fileExists(String filePath) {
        File file = new File(filePath);
        return file.exists();
    }
    
    /**
     * 删除指定文件夹下所有文件, 不保留文件夹.
     */
    public static boolean delAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (file.isFile()) {
            file.delete();
            return true;
        }
        File[] files = file.listFiles();
        for (int i = 0; i < files.length; i++) {
            File exeFile = files[i];
            if (exeFile.isDirectory()) {
                delAllFile(exeFile.getAbsolutePath());
            } else {
                exeFile.delete();
            }
        }
        file.delete();
        
        return flag;
    }
    
    /**
     * 删除目录下的所有文件
     *
     * @param dirPath 目录路径
     * @return {@code true}: 删除成功<br>{@code false}: 删除失败
     */
    public static boolean deleteFilesInDir(String dirPath) {
        return deleteFilesInDir(getFileByPath(dirPath));
    }
    
    /**
     * 删除目录下的所有文件
     *
     * @param dir 目录
     * @return {@code true}: 删除成功<br>{@code false}: 删除失败
     */
    public static boolean deleteFilesInDir(File dir) {
        if (dir == null) {
            return false;
        }
        // 目录不存在返回true
        if (!dir.exists()) {
            return true;
        }
        // 不是目录返回false
        if (!dir.isDirectory()) {
            return false;
        }
        // 现在文件存在且是文件夹
        File[] files = dir.listFiles();
        if (files != null && files.length != 0) {
            for (File file : files) {
                if (file.isFile()) {
                    if (!deleteFile(file)) {
                        return false;
                    }
                } else if (file.isDirectory()) {
                    if (!deleteDir(file)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    
    /**
     * 清除内部缓存
     * <p>/data/data/com.xxx.xxx/cache</p>
     *
     * @return {@code true}: 清除成功<br>{@code false}: 清除失败
     */
    public static boolean cleanInternalCache(Context context) {
        return deleteFilesInDir(context.getCacheDir());
    }
    
    /**
     * 清除内部文件
     * <p>/data/data/com.xxx.xxx/files</p>
     *
     * @return {@code true}: 清除成功<br>{@code false}: 清除失败
     */
    public static boolean cleanInternalFiles(Context context) {
        return deleteFilesInDir(context.getFilesDir());
    }
    
    /**
     * 清除内部数据库
     * <p>/data/data/com.xxx.xxx/databases</p>
     *
     * @return {@code true}: 清除成功<br>{@code false}: 清除失败
     */
    public static boolean cleanInternalDbs(Context context) {
        return deleteFilesInDir(context.getFilesDir().getParent() + File.separator + "databases");
    }
    
    /**
     * 根据名称清除数据库
     * <p>/data/data/com.xxx.xxx/databases/dbName</p>
     *
     * @param dbName 数据库名称
     * @return {@code true}: 清除成功<br>{@code false}: 清除失败
     */
    public static boolean cleanInternalDbByName(Context context, String dbName) {
        return context.deleteDatabase(dbName);
    }
    
    /**
     * 清除内部SP
     * <p>/data/data/com.xxx.xxx/shared_prefs</p>
     *
     * @return {@code true}: 清除成功<br>{@code false}: 清除失败
     */
    public static boolean cleanInternalSP(Context context) {
        return deleteFilesInDir(context.getFilesDir().getParent() + File.separator + "shared_prefs");
    }
    
    /**
     * 清除外部缓存
     * <p>/storage/emulated/0/android/data/com.xxx.xxx/cache</p>
     *
     * @return {@code true}: 清除成功<br>{@code false}: 清除失败
     */
    public static boolean cleanExternalCache(Context context) {
        return isSDCardEnable() && deleteFilesInDir(context.getExternalCacheDir());
    }
    
    /**
     * 清除自定义目录下的文件
     *
     * @param dirPath 目录路径
     * @return {@code true}: 清除成功<br>{@code false}: 清除失败
     */
    public static boolean cleanCustomCache(String dirPath) {
        return deleteFilesInDir(dirPath);
    }
    
    /**
     * 清除自定义目录下的文件
     *
     * @param dir 目录
     * @return {@code true}: 清除成功<br>{@code false}: 清除失败
     */
    public static boolean cleanCustomCache(File dir) {
        return deleteFilesInDir(dir);
    }
    
    /**
     * 删除文件
     *
     * @param srcFilePath 文件路径
     * @return {@code true}: 删除成功<br>{@code false}: 删除失败
     */
    public static boolean deleteFile(String srcFilePath) {
        return deleteFile(getFileByPath(srcFilePath));
    }
    
    /**
     * 删除文件
     *
     * @param file 文件
     * @return {@code true}: 删除成功<br>{@code false}: 删除失败
     */
    public static boolean deleteFile(File file) {
        return file != null && (!file.exists() || file.isFile() && file.delete());
    }
    
    /**
     * 根据文件路径获取文件
     *
     * @param filePath 文件路径
     * @return 文件
     */
    public static File getFileByPath(String filePath) {
        return StringUtils.isEmpty(filePath) ? null : new File(filePath);
    }
    
    /**
     * 删除目录
     *
     * @param dirPath 目录路径
     * @return {@code true}: 删除成功<br>{@code false}: 删除失败
     */
    public static boolean deleteDir(String dirPath) {
        return deleteDir(getFileByPath(dirPath));
    }
    
    /**
     * 删除目录
     *
     * @param dir 目录
     * @return {@code true}: 删除成功<br>{@code false}: 删除失败
     */
    public static boolean deleteDir(File dir) {
        if (dir == null) {
            return false;
        }
        // 目录不存在返回true
        if (!dir.exists()) {
            return true;
        }
        // 不是目录返回false
        if (!dir.isDirectory()) {
            return false;
        }
        // 现在文件存在且是文件夹
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                if (!deleteFile(file)) {
                    return false;
                }
            } else if (file.isDirectory()) {
                if (!deleteDir(file)) {
                    return false;
                }
            }
        }
        return dir.delete();
    }
    
}