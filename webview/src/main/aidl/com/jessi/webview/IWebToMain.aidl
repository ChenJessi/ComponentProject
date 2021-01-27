// IWebToMain.aidl
package com.jessi.webview;
import com.jessi.webview.ICallbackFromMainToWeb;
// Declare any non-default types here with import statements

interface IWebToMain {

    void handleWebAction(String actionName, String jsonParams, in ICallbackFromMainToWeb callback);

}