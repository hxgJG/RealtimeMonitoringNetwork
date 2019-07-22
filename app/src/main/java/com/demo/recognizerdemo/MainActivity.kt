package com.demo.recognizerdemo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.demo.library.NetworkManager
import com.demo.library._enum.NetType
import com.demo.library.annotation.Network

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        NetworkManager.getDefault().register(this)
    }

    @Network
    fun network(type: NetType) {
        Log.e("hxg", "network --> type = ${type.name}")
    }

    override fun onDestroy() {
        super.onDestroy()
        NetworkManager.getDefault().unregister(this)
    }
}
