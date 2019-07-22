package com.demo.recognizerdemo;

import android.app.Application;

import com.demo.library.NetworkManager;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        NetworkManager.getDefault().init(this);
    }
}
