package com.jessi.webview.commands

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.jessi.webview.command.Command
import com.jessi.webview.command.ResultBack

private const val TAG = "ToastCommand"
class ToastCommand : Command {
    override fun name(): String {
        return "toast"
    }

    override fun exec(context: Context, params: MutableMap<*, *>, resultBack: ResultBack) {
        Log.e(TAG, "exec: params : ${params.toString()}   resultBack : $resultBack ")
        Toast.makeText(context, "这是一个toast", Toast.LENGTH_SHORT).show()
    }
}