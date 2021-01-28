package com.jessi.webview.command

import android.content.Context
import android.webkit.WebView
import com.jessi.webview.remotewebview.utils.AidlError
import com.jessi.webview.remotewebview.utils.ERROR_CODE
import com.jessi.webview.remotewebview.utils.ERROR_MESSAGE
import com.jessi.webview.remotewebview.utils.FAILED


private const val TAG = "CommandsManager"
object CommandsManager {
    private val commands = mutableMapOf<String, Command>()

    fun getCommands() = commands

    fun registerCommand(command: Command){
        commands[command.name()] = command
    }


    /**
     *
     */
    fun execMainProcessCommand(context: Context, action: String, params: MutableMap<*, *>, resultBack: ResultBack){
        if (getCommands()[action] != null){
            getCommands()[action]?.exec(context, params, resultBack)
        }else{
            val aidlError = AidlError(ERROR_CODE.NO_METHOD, ERROR_MESSAGE.NO_METHOD)
            resultBack.onResult(FAILED, action,  aidlError)
        }
    }


    /**
     * WebView处理命令
     */
    fun execWebViewProcessCommand(context: Context, action: String, params : MutableMap<*, *>, resultBack :ResultBack){
            getCommands()[action]?.exec(context, params, resultBack)
    }

    /**
     * 是否是webView 的命令
     */
    fun isWebViewProcessCommand(action : String) = getCommands()[action] != null


}