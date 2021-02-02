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
import com.jessi.componentproject.databinding.ActivityMainBinding
import com.jessi.componentproject.databinding.ActivitySecondBinding

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this , R.layout.activity_main)

        binding.button.setOnClickListener {
            test()
        }
    }

    fun test(){
        Log.e(TAG, "test: ===============")
        var selectionIntent = Intent(Intent.ACTION_PICK, null).apply {
            type = "image/*"
        }

        val intentArray = arrayOf<Intent>()
        val chooserIntent = Intent(Intent.ACTION_CHOOSER).apply {
            putExtra(Intent.EXTRA_TITLE, getString(com.jessi.webview.R.string.file_chooser))
            putExtra(Intent.EXTRA_INTENT, selectionIntent)
            putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray)
        }

        registerForActivityResult(object : ActivityResultContracts.TakePicturePreview(){}) {
            Log.e(TAG, "test:   ${it}")
        }.launch(null)

    }
}

