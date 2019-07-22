package com.demo.library.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.demo.library.NetworkManager;
import com.demo.library._enum.NetType;

public class NetworkUtils {
    @SuppressLint("MissingPermission")
    public static boolean isNetworkAvailable () {
        ConnectivityManager connMgr = (ConnectivityManager) NetworkManager.getDefault()
                .getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connMgr == null) return false;

        NetworkInfo[] infos = connMgr.getAllNetworkInfo();
        if (infos != null) {
            for (NetworkInfo info: infos) {
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }

    @SuppressLint("MissingPermission")
    public static NetType getNetType() {
        ConnectivityManager connMgr = (ConnectivityManager)NetworkManager.getDefault()
                .getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connMgr == null) return NetType.NONE;

        NetworkInfo info = connMgr.getActiveNetworkInfo();
        if (info == null) {
            return NetType.NONE;
        }

        int typeCode = info.getType();

        if (typeCode == ConnectivityManager.TYPE_MOBILE) {
            if (info.getExtraInfo().toLowerCase().equals("cmnet")) {
                return NetType.CMNET;
            } else  {
                return NetType.CMWAP;
            }
        } else if (typeCode == ConnectivityManager.TYPE_WIFI) {
            return NetType.WIFI;
        } else {
            return NetType.NONE;
        }
    }

    public static void openSetting(Context context, int requestCode) {
        Intent intent = new Intent("/");
        ComponentName cm = new ComponentName("com.android.settings", "com.android.settings.WirelessSettings");
        intent.setComponent(cm);
        intent.setAction("android.intent.action.VIEW");
        ((Activity)context).startActivityForResult(intent, requestCode);
    }
}
