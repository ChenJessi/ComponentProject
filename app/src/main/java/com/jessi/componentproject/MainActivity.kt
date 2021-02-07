package com.jessi.componentproject

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import com.google.gson.Gson
import com.jessi.componentproject.databinding.ActivityMainBinding
import com.jessi.componentproject.databinding.ActivitySecondBinding
import com.jessi.webview.WebViewActivity

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this , R.layout.activity_main)

        binding.button.setOnClickListener {
            WebViewActivity.newInstance(this@MainActivity , "https://www.baidu.com/", "百度")
        }
    }


}

