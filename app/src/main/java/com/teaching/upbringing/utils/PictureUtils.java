package com.teaching.upbringing.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class PictureUtils {
    /**
     * 将位图转换成文件输入sd卡中
     */
    public static boolean data2file(Bitmap b, String fileName) {
        boolean result = false;
        try {
            File file = new File(fileName);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            FileOutputStream fout = new FileOutputStream(file);
            BufferedOutputStream bos = new BufferedOutputStream(fout);
            //压缩率为10%
            b.compress(Bitmap.CompressFormat.JPEG, 90, bos);
            bos.flush();
            bos.close();
            fout.close();
            bos = null;
            fout.close();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        b = null;
        System.gc();
        return result;
    }


    /**
     * 将io流转换成字节数组
     *
     * @param is
     * @return
     */
    public static byte[] ioToByteArray(InputStream is) {
        //将输入流 转换为字节数组
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            while ((len = is.read(buffer)) != -1) {
                bos.write(buffer, 0, len);
            }
            bos.flush();
            return bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }
    }


    public static Bitmap compressBitmap(byte[] pictureByte, double fileSize) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        options.inJustDecodeBounds = false;
        if (fileSize > 0.3 && fileSize < 1.5) {
            options.inSampleSize = 2;
        } else if (fileSize > 1.5) {
            options.inSampleSize = 6;
        } else if (fileSize >= 3) {
            options.inSampleSize = 8;
        }
        options.inPurgeable = true;
        options.inInputShareable = true;
        Bitmap bitmap = null;// data是字节数据，将其解析成位图
        if (pictureByte != null) {
            bitmap = BitmapFactory.decodeByteArray(pictureByte, 0, pictureByte.length, options);
        }
        return bitmap;
    }


}
