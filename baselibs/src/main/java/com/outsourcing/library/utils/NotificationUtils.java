package com.outsourcing.library.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import androidx.core.app.NotificationManagerCompat;

public class NotificationUtils {
    
    /**
     * 检查有无通知显示权限
     */
    public static boolean hasPermission(Context context) {
        NotificationManagerCompat manager = NotificationManagerCompat.from(context);
        return manager.areNotificationsEnabled();
    }
    
    public static void init() {
    
    }
    
    /**
     * 到设置界面
     */
    public static void toSetting(Context context) {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", context.getPackageName(), null);
        intent.setData(uri);
        context.startActivity(intent);
    }
    
    private void createNotificationChannel(Context context,String groupId,String groupName,String channelId,String channelName) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            
            //分组（可选）
            //groupId要唯一
            NotificationChannelGroup group = new NotificationChannelGroup(groupId, groupName);
            
            //创建group
            if (notificationManager == null) {
                return;
            }
            notificationManager.createNotificationChannelGroup(group);
            
            //channelId要唯一
            NotificationChannel adChannel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
            //补充channel的含义（可选）
            adChannel.setDescription(channelName);
            //将渠道添加进组（先创建组才能添加）
            adChannel.setGroup(groupId);
            //创建channel
            notificationManager.createNotificationChannel(adChannel);
        }
    }
    
    public static void sendNotification(Context context,int notificationId,Intent intent,String channelId,
                                        int resSmallIcon,int resLargeIcon,
                                        String contentTitle,String contentText,boolean canCancel) {
        
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Notification.Builder builder = new Notification.Builder(context, channelId)
                    .setSmallIcon(resSmallIcon)
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), resLargeIcon))
                    .setContentTitle(contentTitle)
                    .setContentText(contentText)
                    .setAutoCancel(canCancel)
                    .setContentIntent(pendingIntent);
            if (notificationManager != null) {
                notificationManager.notify(notificationId, builder.build());
            }
            
        } else {
            //获取NotificationManager实例
            Notification.Builder builder = new Notification.Builder(context)
                    .setAutoCancel(true)
                    //设置小图标
                    .setSmallIcon(resSmallIcon)
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), resLargeIcon))
                    //设置通知标题
                    .setContentTitle(contentTitle)
                    //设置通知内容
                    .setContentText(contentText)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    //设置小图标
                    .setContentIntent(pendingIntent);
            notificationManager.notify(notificationId, builder.build());
        }
    }
    
    /**
     * 估价报告group
     */
    interface GroupEvaluate{
        String GROUP_EVALUATE_ID = "group_evaluate";
        String GROUP_EVALUATE_NAME = "估价报告";
        
        interface Channel{
            String CHANNEL_FAST = "快速估值";
            String CHANNEL_HISTORY = "车史定价";
            String CHANNEL_ACCURATE = "精准定价";
        }
    }
}
