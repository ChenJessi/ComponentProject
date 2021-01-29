package com.jessi.webview.utils

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class AidlError(val code : Int, val message : String) :Parcelable