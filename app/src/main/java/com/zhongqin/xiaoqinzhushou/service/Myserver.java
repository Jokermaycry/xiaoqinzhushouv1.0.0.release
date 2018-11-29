package com.zhongqin.xiaoqinzhushou.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;



public class Myserver extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("=========onStartCommand======");

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("=========onCreate======");

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
