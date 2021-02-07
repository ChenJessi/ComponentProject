package com.jessi.webview

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.gson.Gson
import com.jessi.webview.databinding.ActivityWebviewBinding
import com.jessi.webview.utils.INTENT_TAG_TITLE
import com.jessi.webview.utils.INTENT_TAG_URL
import com.jessi.webview.view.ACCOUNT_INFO_HEADERS
import com.jessi.webview.view.BaseWeViewFragment


private const val TAG = "WebViewActivity"
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



    val title : String by lazy { intent?.getStringExtra(INTENT_TAG_TITLE) ?: ""}
    val url : String by lazy { intent?.getStringExtra(INTENT_TAG_URL) ?: ""}
    val webViewFragment : WebViewFragment by lazy {  WebViewFragment.newInstance(url) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityWebviewBinding>(this, R.layout.activity_webview)

        setTitle(title)

        val fm = supportFragmentManager
        val transaction = fm.beginTransaction().apply {
            replace(R.id.frameLayout, webViewFragment)
            commit()
        }
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                val flag = webViewFragment.onKeyDown()
                if (!flag){
                    finish()
                }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}