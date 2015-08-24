package com.example.hzzhanyawei.service;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

public class MyService extends Service {

    public final static String DEBUG = "MyService";
    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Notification notification = new Notification(R.drawable.ic_launcher, "Notification comes", System. currentTimeMillis());
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        notification.setLatestEventInfo(this, "This is title", "This is content", pendingIntent);
        startForeground(1, notification);

        Log.d(DEBUG, "service create");
        Log.d(DEBUG, "create service thread is " + Thread.currentThread());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(DEBUG, "service start command");
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                // 处理具体的逻辑
//            }
//        }).start();
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        int anHour = 60 * 60 * 1000;  // 这是一小时的毫秒数
        long triggerAtTime = SystemClock.elapsedRealtime() + anHour;
        Intent i = new Intent(this, AlarmReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(DEBUG, "service unbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(DEBUG, "service destroy");
    }

    class MyBinder extends Binder {

        public void startDownload() {
            Log.d(DEBUG, "mybinder service thread is " + Thread.currentThread());
            Log.d(DEBUG, "startDownload executed");
        }

        public int getProgress() {
            Log.d(DEBUG, "getProgress executed");
            return 0;
        }
    }

    public class AlarmReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Intent i = new Intent(context, MyService.class);
            context.startService(i);
        }
    }

}
