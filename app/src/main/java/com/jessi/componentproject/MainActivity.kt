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

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this , R.layout.activity_main)

        binding.button.setOnClickListener {
            val map = mutableMapOf<String, String>().apply {
                put("aaa", "aaa")
                put("bbb", "bbb")
                put("ccc", "ccc")
            }
            Log.e(TAG, "onCreate:   map  : $map")
            val a = Gson().toJson(map)
            Log.e(TAG, "onCreate:   a : $a")
            val b = Gson().fromJson<MutableMap<String, String>>(a, MutableMap::class.java).apply {
                put("ddd", "ddd")
            }
            Log.e(TAG, "onCreate:   a : $b")
        }
    }


}

