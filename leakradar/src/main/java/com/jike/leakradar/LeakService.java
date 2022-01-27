package com.jike.leakradar;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class LeakService extends Service {

    private static final String CHANNEL_ID = "LeakRadar";

    private volatile static int notifyId = 0;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String info = intent.getStringExtra("Info");
        String detail = intent.getStringExtra("Detail");
        notificate(info, detail);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void notificate(String info, String detail){

        NotificationManager notificationManager=(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = null;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, "Leak",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(notificationChannel);
            builder = new NotificationCompat.Builder(getApplicationContext(),CHANNEL_ID);
        } else {
            builder = new NotificationCompat.Builder(getApplicationContext());
        }

        Intent intent = new Intent(this, LeakActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        //Ticker是状态栏显示的提示
        builder.setTicker("显示setTicker");
        //第一行内容  通常作为通知栏标题
        builder.setContentTitle(info);
        //第二行内容 通常是通知正文
        builder.setContentText(detail);
        //第三行内容 通常是内容摘要什么的 在低版本机器上不一定显示
        builder.setSubText("LeakRadar");
        //ContentInfo 在通知的右侧 时间的下面 用来展示一些其他信息
        builder.setContentInfo("这里显示ContentInfo");
        //number设计用来显示同种通知的数量和ContentInfo的位置一样，如果设置了ContentInfo则number会被隐藏
        builder.setNumber(2);
        //true：点击通知栏，通知消失
        builder.setAutoCancel(true);
        //通知时间
        builder.setWhen(System.currentTimeMillis());
        //系统状态栏显示的小图标
        builder.setSmallIcon(R.mipmap.ic_launcher_leak);
        //builder.setLargeIcon(BitmapUtils.getBitmapById(getApplicationContext(), com.jike.leakradar.R.mipmap.ic_launcher_leak));
        builder.setContentIntent(pendingIntent);
        //下拉显示的大图标
        //通知默认的声音 震动 呼吸灯
        builder.setDefaults(NotificationCompat.DEFAULT_ALL);
        Notification notification = builder.build();

        notificationManager.notify(notifyId++ , notification);

    }
}
