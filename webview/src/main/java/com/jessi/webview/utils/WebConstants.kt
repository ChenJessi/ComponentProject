package com.jessi.webview.utils

const val LEVEL_REMOTE_PROCESS = 0 // local command, that is to say, this command execution does not require app.

const val LEVEL_MAIN_PROCESS = 2 // 涉及到账号相关的level


const val CONTINUE = 2 // 继续分发command

const val SUCCESS = 1 // 成功

const val FAILED = 0 // 失败

const val EMPTY = "" // 无返回结果


const val WEB2NATIVE_CALLBACk = "callback"
const val NATIVE2WEB_CALLBACK = "callbackname"

const val ACTION_EVENT_BUS = "eventBus"

const val INTENT_TAG_TITLE = "title"
const val INTENT_TAG_URL = "url"
const val INTENT_TAG_HEADERS = "headers"

object ERROR_CODE {
    const val NO_METHOD = -1000
    const val NO_AUTH = -1001
    const val NO_LOGIN = -1002
    const val ERROR_PARAM = -1003
    const val ERROR_EXCEPTION = -1004
}

object ERROR_MESSAGE {
    const val NO_METHOD = "方法找不到"
    const val NO_AUTH = "方法权限不够"
    const val NO_LOGIN = "尚未登录"
    const val ERROR_PARAM = "参数错误"
    const val ERROR_EXCEPTION = "未知异常"
}