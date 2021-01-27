package com.jessi.webview.mainprocess

import android.content.Context
import com.jessi.webview.ICallbackFromMainToWeb
import com.jessi.webview.IWebToMain


class MainProAidlInterface(var context : Context) : IWebToMain.Stub() {
    override fun handleWebAction(actionName: String?, jsonParams: String?, callback: ICallbackFromMainToWeb?) {

    }


}