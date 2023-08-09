package com.example.myapplication;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
//tạo và quản lý các kênh thông báo trong Android
public class ApplicationClass extends Application {
    public static final String CHANNEL_ID_1 = "channel1";
    public static final String CHANNEL_ID_2 = "channel2";
    public static final String ACTION_PREVIOUS = "actionprevious";
    public static final String ACTION_NEXT = "actionnext";
    public static final String ACTION_PLAY = "actionplay";

    @Override
    public void onCreate() {
        super.onCreate();
        createNoficationChannel();
    }

    private void createNoficationChannel() {
        //Đoạn mã này kiểm tra xem phiên bản Android của thiết bị có tương thích với việc tạo các kênh thông báo hay không
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel channel1 =
                    new NotificationChannel(CHANNEL_ID_1,
                            "channel(1)", NotificationManager.IMPORTANCE_HIGH);
            channel1.setDescription("channel 1 Desc...");

            NotificationChannel channel2 =
                    new NotificationChannel(CHANNEL_ID_2,
                            "channel(2)", NotificationManager.IMPORTANCE_HIGH);
            channel2.setDescription("channel 2 Desc...");
            NotificationManager notificationManager =
                    getSystemService(NotificationManager.class);
            // Đoạn mã này sử dụng notificationManager để tạo các kênh thông báo đã được cấu hình
            notificationManager.createNotificationChannel(channel1);
            notificationManager.createNotificationChannel(channel2);
        }
    }

}
