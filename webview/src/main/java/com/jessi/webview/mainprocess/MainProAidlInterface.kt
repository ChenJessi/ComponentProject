package com.jessi.webview.mainprocess

import android.content.Context
import com.google.gson.Gson
import com.jessi.webview.ICallbackFromMainToWeb
import com.jessi.webview.IWebToMain
import com.jessi.webview.command.CommandsManager
import com.jessi.webview.command.ResultBack


class MainProAidlInterface(var context : Context) : IWebToMain.Stub() {
    override fun handleWebAction(actionName: String?, jsonParams: String?, callback: ICallbackFromMainToWeb?) {
        CommandsManager.execMainProcessCommand(context, actionName.toString(), Gson().fromJson(jsonParams, MutableMap::class.java), object : ResultBack{
            override fun onResult(status: Int, action: String, result: Any?) {
                try {
                    callback?.onResult(status, action, Gson().toJson(result))
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        })
    }


}