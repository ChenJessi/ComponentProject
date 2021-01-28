package com.jessi.webview.command

import android.content.Context

interface Command {
    fun name() : String
    fun exec(context: Context, params: MutableMap<*, *>, resultBack : ResultBack)
}