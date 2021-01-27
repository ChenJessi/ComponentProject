package com.jessi.webview.mainprocess

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class MainProHandleRemoteService  : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        val pid = android.os.Process.myPid()
        Log.d("", "MainProHandleRemoteService:  当前进程ID为：$pid----客户端与服务端连接成功，服务端返回BinderPool.BinderPoolImpl 对象")
        return MainProAidlInterface(this)
    }
}