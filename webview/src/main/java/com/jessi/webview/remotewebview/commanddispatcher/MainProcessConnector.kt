package com.jessi.webview.remotewebview.commanddispatcher

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import com.jessi.webview.IWebToMain
import com.jessi.webview.mainprocess.MainProHandleRemoteService
import java.util.concurrent.CountDownLatch


/**
 * 用于remoteweb process 向 main process 获取binder
 */
class MainProcessConnector private constructor() {
    private var mContext : Context? = null
    private var mConnectBinderPoolCountDownLatch : CountDownLatch? = null

    private var iWebToMain : IWebToMain? = null

    companion object{
        private val instance : MainProcessConnector by lazy (mode = LazyThreadSafetyMode.SYNCHRONIZED){
            MainProcessConnector()
        }
        fun instance(context: Context) : MainProcessConnector {
            instance.mContext = context.applicationContext
            return instance
        }
    }

    init {
        connectToMainProcessService()
    }
    @Synchronized
    private fun  connectToMainProcessService(){
        mConnectBinderPoolCountDownLatch = CountDownLatch(1)
        val service = Intent(mContext, MainProHandleRemoteService::class.java)
        mContext?.bindService(service, mBinderPoolConnection, Context.BIND_AUTO_CREATE)

        try {
            mConnectBinderPoolCountDownLatch?.await()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    fun getIWebAidlInterface() : IBinder?  = iWebToMain?.asBinder()


    private val  mBinderPoolConnection = object : ServiceConnection{
        override fun onServiceDisconnected(name: ComponentName?) {

        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            iWebToMain = IWebToMain.Stub.asInterface(service)
            try {
                iWebToMain?.asBinder()?.linkToDeath(mBinderPoolDeathRecipient, 0)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            mConnectBinderPoolCountDownLatch?.countDown()
        }
    }

    private val mBinderPoolDeathRecipient = object : IBinder.DeathRecipient {
        override fun binderDied() {
            iWebToMain?.asBinder()?.unlinkToDeath(this, 0)
            iWebToMain = null
            connectToMainProcessService()
        }
    }


}