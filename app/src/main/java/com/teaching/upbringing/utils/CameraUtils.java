package com.teaching.upbringing.utils;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import com.lefore.tutoring.R;

import java.io.FileInputStream;
import java.io.IOException;

public class CameraUtils {
    /**
     * 处理系统相机拍摄的图片
     */
    public static boolean savePicture(String filePath) {
        FileInputStream is = null;
        try {
            is = new FileInputStream(filePath);
            double fileSize = (double) (is.available()) / (double) (1024 * 1024);
            System.out.println("转换前文件大小为------------->" + fileSize + "M");
            System.out.println("开始处理文件------------->" + System.currentTimeMillis());
            
            //将io流转换成字节数组
            byte[] pictureByte = PictureUtils.ioToByteArray(is);
            //将字节数组处理成bitmap图片
            Bitmap bitmap = PictureUtils.compressBitmap(pictureByte, fileSize);
            //将bitmap图片存入文件中
            PictureUtils.data2file(bitmap, filePath);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    /**
     * 处理图库返回的图片
     *
     * @param data
     * @return 图片地址
     */
    public static String handlerChoosePic(Activity context, Intent data) {
        // 选择图片
        Uri selectedImage = data.getData();
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(selectedImage, filePathColumn, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            if (picturePath.toLowerCase().endsWith(".jpg")||picturePath.toLowerCase().endsWith(".jpeg")) {
                if (savePicture(picturePath)) {
                    return picturePath;
                }
                return "";
            } else {
                ToastUtil.showLong(context, "请选择JPG格式图片", R.mipmap.icon_fail);
                return "";
            }
        } else {
            ToastUtil.showLong(context, "图片选择错误", R.mipmap.icon_fail);
            return "";
        }
    }
}
