package com.jessi.webview

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.jessi.webview.databinding.ActivityWebviewBinding
import com.jessi.webview.utils.INTENT_TAG_TITLE
import com.jessi.webview.utils.INTENT_TAG_URL

class WebViewActivity : AppCompatActivity() {


    companion object{
        fun newInstance(context : Context, url: String, title: String = ""){
            val intent = Intent(context, WebViewActivity::class.java).apply {
                putExtra(INTENT_TAG_URL, url)
                putExtra(INTENT_TAG_TITLE, title)
            }
            context.startActivity(intent)
        }
    }



    val title : String by lazy { intent.getStringExtra(INTENT_TAG_TITLE) ?: ""o }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityWebviewBinding>(this, R.layout.activity_webview)


    }
}