package com.jessi.webview.view

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.content.Intent.*
import android.net.Uri
import android.os.Bundle
import android.os.Looper
import android.view.KeyEvent
import android.view.KeyEvent.ACTION_DOWN
import android.view.KeyEvent.KEYCODE_BACK
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.ValueCallback
import android.webkit.WebView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.jessi.webview.R
import com.jessi.webview.command.CommandsManager
import com.jessi.webview.commands.ToastCommand
import com.jessi.webview.remotewebview.ProgressWebView
import com.jessi.webview.remotewebview.callback.WebViewCallBack
import com.jessi.webview.utils.INTENT_TAG_URL


const val ACCOUNT_INFO_HEADERS = "account_header"

const val REQUEST_CODE_LOLIPOP = 1

private const val TAG = "BaseWeViewFragment"
internal abstract class BaseWeViewFragment : Fragment(), WebViewCallBack {

    private var accountInfoHeaders : MutableMap<String, String>? = null
    private var webUrl : String? = null
    private var webView : ProgressWebView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = arguments?.apply {
             webUrl = getString(INTENT_TAG_URL)
            if (containsKey(ACCOUNT_INFO_HEADERS)){
                val str = getString(ACCOUNT_INFO_HEADERS)
                accountInfoHeaders = Gson().fromJson<MutableMap<String, String>>(str, MutableMap::class.java)
            }
        }
        CommandsManager.registerCommand(ToastCommand())
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_common_webview, container, false)
         webView = view.findViewById<ProgressWebView>(R.id.webView)
        accountInfoHeaders?.apply {
            webView?.setHeaders(this)
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        webView?.registeredWebViewCallBack(this)
        loadUrl()
    }

    private fun loadUrl(){
        if (webUrl.isNullOrEmpty()){
            webView?.loadUrl(webUrl!!)
        }else{
            Toast.makeText(activity, "WebView url is can't empty!", Toast.LENGTH_SHORT).show()
        }

    }


    override fun onResume() {
        super.onResume()
        webView?.dispatchEvent("pageResume")
        webView?.onResume()
    }

    override fun onPause() {
        super.onPause()
        webView?.dispatchEvent("pagePause")
        webView?.onResume()
    }

    override fun onStop() {
        super.onStop()
        webView?.dispatchEvent("pageStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        webView?.apply {
            dispatchEvent("pageDestroy")
            clearWebView(this)
        }
    }



    override fun pageStarted(url: String?) {

    }

    override fun pageFinished(url: String?) {

    }

    override fun onError() {

    }

    private var mFilePathCallback: ValueCallback<Array<Uri?>?>? = null

    override fun onShowFileChooser(
        cameraIntent: Intent?,
        filePathCallback: ValueCallback<Array<Uri?>?>?
    ) {
        /**
         * 整个弹窗为相机，相册，文件管理
         *  selectionIntent(相册、文件管理)
         *  Intent selectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
         *  selectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
         *  selectionIntent.setType("image/\*")
         */
        mFilePathCallback = filePathCallback
        /**
         * 如果通过下面的方式，则弹出的选择框有:相机、相册(Android9.0,Android8.0)
         * 如果是小米Android6.0系统上，依然是：相机、相册、文件管理
         * 如果安装了其他的相机(百度魔拍)、文件管理程序(ES文件管理器)，也有可能会弹出
         */
        var selectionIntent = Intent(ACTION_PICK, null).apply {
            type = "image/*"
        }

        val intentArray = if (cameraIntent == null) arrayOf<Intent>()  else arrayOf(cameraIntent)
        val chooserIntent = Intent(ACTION_CHOOSER).apply {
            putExtra(EXTRA_TITLE, getString(R.string.file_chooser))
            putExtra(EXTRA_INTENT, selectionIntent)
            putExtra(EXTRA_INITIAL_INTENTS, intentArray)
        }

        registerForActivityResult(object : ActivityResultContract<Intent, String?>(){
            override fun createIntent(context: Context, input: Intent?): Intent {
                return chooserIntent
            }

            override fun parseResult(resultCode: Int, intent: Intent?): String? {
                return when(resultCode){
                    RESULT_OK -> {
                        intent?.dataString
                    }
                    else -> null

                }
            }
        }
        ) {

        }.launch(chooserIntent)

    }


    private fun onKeyDown(keyCode : Int , event : KeyEvent) : Boolean{
        if (keyCode == KEYCODE_BACK && event.action == ACTION_DOWN){
            return onBackHandle()
        }
        return false
    }

    private fun onBackHandle() : Boolean{
        if (webView?.canGoBack() == true){
            webView?.goBack()
            return true
        }else{
            return false
        }
    }




    private fun clearWebView(webView : WebView?){
        var w = webView
        if (Looper.myLooper() != Looper.getMainLooper()){
            return
        }
        w?.apply {
            stopLoading()
            handler?.removeCallbacksAndMessages(null)
            removeAllViews()
            parent?.let {
                (it as ViewGroup)?.removeView(this)
            }
            webChromeClient = null
//            webViewClient = null
            tag = null
            clearHistory()
            destroy()
        }
        w = null
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

}

class a :AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


}