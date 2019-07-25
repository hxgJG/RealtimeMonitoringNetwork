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
@Network
fun network(type: NetType) {
    Log.e("test", "network --> type = ${type.name}")
}
```

##### finally: unregister after not use it
```
NetworkManager.getDefault().unregister(this)
```
