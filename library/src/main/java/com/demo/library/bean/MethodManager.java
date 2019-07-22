package com.demo.library.bean;

import com.demo.library._enum.NetType;

import java.lang.reflect.Method;

public class MethodManager {
    private Class<?> type;
    private NetType netType;
    private Method method;

    public MethodManager(Class<?> type, NetType netType, Method method) {
        this.type = type;
        this.netType = netType;
        this.method = method;
    }

    public Class<?> getType() {
        return type;
    }

    public NetType getNetType() {
        return netType;
    }

    public Method getMethod() {
        return method;
    }
}
