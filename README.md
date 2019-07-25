# RealtimeMonitoringNetwork

Real-time network monitoring framework

### How to use it?

##### first: init in onCreate in Application
```
NetworkManager.getDefault().init(this);
```

##### second: register before use it
```
NetworkManager.getDefault().register(this)
```

##### third: use @Network annotation
```
@Network(netType = NetType.WIFI)
public void network(NetType type) {
    Log.i("test", type.toString());
}
```

##### finally: unregister after not use it
```
NetworkManager.getDefault().unregister(this)
```
