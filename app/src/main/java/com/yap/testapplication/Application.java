package com.yap.testapplication;

import cn.jpush.android.api.JPushInterface;

public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }
}
