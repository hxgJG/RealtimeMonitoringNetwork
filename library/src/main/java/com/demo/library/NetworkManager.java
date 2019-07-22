package com.demo.library;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.IntentFilter;

import org.jetbrains.annotations.NotNull;

public class NetworkManager {

    private static volatile NetworkManager instance;
    private Application application;
    private NetStateReceiver receiver;

    private NetworkManager() {
        receiver = new NetStateReceiver();

    }

    public static NetworkManager getDefault() {
        if (instance == null) {
            synchronized (NetworkManager.class) {
                if (instance == null) {
                    instance = new  NetworkManager();
                }
            }
        }
        return instance;
    }

    @SuppressLint("MissingPermission")
    public void init(Application application) {
        if (application == null) return;
        this.application = application;

        // 方式一：使用广播监听
        IntentFilter filter = new IntentFilter();
        filter.addAction(Const.ANDROID_NET_CHANGE_ACTION);
        application.registerReceiver(receiver, filter);

        // 方式二： 直接监听网络状态，但是要求api在21及以上
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            ConnectivityManager.NetworkCallback networkCallback = new NetworkCallbackImpl();
//            NetworkRequest.Builder builder = new NetworkRequest.Builder();
//            NetworkRequest request = builder.build();
//            ConnectivityManager cmgr = (ConnectivityManager) NetworkManager.getDefault().getApplication()
//                    .getSystemService(Context.CONNECTIVITY_SERVICE);
//            if (cmgr != null) {
//                cmgr.registerNetworkCallback(request, networkCallback);
//            }
//        }
    }

    public Application getApplication() {
        if (application == null) {
            throw new RuntimeException("application is null, need init first");
        }
        return application;
    }

    public void register(@NotNull Object register) {
        receiver.register(register);
    }

    public void unregister(@NotNull Object register) {
        receiver.unregister(register);
    }

    public void unregisterAll() {
        receiver.unregisterAll();
    }
}
