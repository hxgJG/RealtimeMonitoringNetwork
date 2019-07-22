package com.demo.library;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.demo.library._enum.NetType;
import com.demo.library.annotation.Network;
import com.demo.library.bean.MethodManager;
import com.demo.library.util.NetworkUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class NetStateReceiver extends BroadcastReceiver {
    private NetType netType;

    private Map<Object, List<MethodManager>> networkList;

    public NetStateReceiver() {
        netType = NetType.NONE;
        networkList = new HashMap<>();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null || intent.getAction() == null) return;

        if (intent.getAction().equalsIgnoreCase(Const.ANDROID_NET_CHANGE_ACTION)) {
            netType = NetworkUtils.getNetType();
            post(netType);
        }
    }

    private void post(NetType netType) {
        if (networkList == null) return;
        Set<Object> set = networkList.keySet();
        for (Object getter : set) {
            List<MethodManager> list = networkList.get(getter);
            if (list != null) {
                for (MethodManager method : list) {
                    if (method.getType().isAssignableFrom(netType.getClass())) {
                        switch (method.getNetType()) {
                            case AUTO:
                                invoke(method, getter, netType);
                                break;
                            case NONE:
                                break;
                        }
                    }
                }
            }
        }
    }

    private void invoke(MethodManager method, Object getter, NetType netType) {
        Method execute = method.getMethod();
        try {
            execute.invoke(getter, netType);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void register(Object register) {
        if (networkList == null) return;
        List<MethodManager> methodManagers = networkList.get(register);
        if (methodManagers == null) {
            methodManagers = findAnnotationMethod(register);
            networkList.put(register, methodManagers);
        }
    }

    private List<MethodManager> findAnnotationMethod(Object register) {
        List<MethodManager> list = new ArrayList<>();
        Class<?> clazz = register.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            Network network = method.getAnnotation(Network.class);
            if (network == null) {
                continue;
            }

            Type returnType = method.getGenericReturnType();
            if (!"void".equalsIgnoreCase(returnType.toString())) {
                throw new RuntimeException(method.getName() + " is not what we want");
            }

            Class<?>[] parameterTypes = method.getParameterTypes();
            if (parameterTypes.length != 1) {
                throw new RuntimeException(method.getName() + " is not what we want, wrong parameter type");
            }

            list.add(new MethodManager(parameterTypes[0], network.netType(), method));
        }
        return list;
    }

    public void unregister(Object register) {
        if (!networkList.isEmpty()) {
            networkList.remove(register);
        }
        Log.d("hxg", "unregister: " + register.getClass().getName());
    }

    public void unregisterAll() {
        if (!networkList.isEmpty()) {
            networkList.clear();
        }
        NetworkManager.getDefault().getApplication().unregisterReceiver(this);
        networkList = null;
        Log.d("hxg", "unregister all");
    }
}
