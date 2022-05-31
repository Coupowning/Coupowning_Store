package kr.ac.coukingmama.storeapp.before

import android.os.Bundle
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import kr.ac.coukingmama.storeapp.databinding.ActivityAddressBinding


class AddressActivity : AppCompatActivity() {

    lateinit var binding : ActivityAddressBinding
    lateinit var webView : WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)
        webView = binding.webView
//        showAddressWebView()
    }
//
//    private fun showAddressWebView() {
//        webView.settings.apply {
//            javaScriptEnabled = true
//            javaScriptCanOpenWindowsAutomatically = true
//            setSupportMultipleWindows(true)
//        }
//        webView.apply {
//            addJavascriptInterface(WebViewData(), "coupowning")
//            webViewClient = client
//            webChromeClient = chromeClient
//            loadUrl("https://coupowning.web.app/")
//        }
//    }
//
//    private val client: WebViewClient = object : WebViewClient() {
//        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
//            return false
//        }
//        override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
//            handler?.proceed()
//        }
//    }
//
//    private inner class WebViewData {
//        @JavascriptInterface
//        fun getAddress(roadAddress: String) {
//            CoroutineScope(Dispatchers.Default).launch {
//                withContext(CoroutineScope(Dispatchers.Main).coroutineContext) {
//                    val address = roadAddress
//                    Toast.makeText(applicationContext, address, Toast.LENGTH_SHORT).show()
//                    startActivity(Intent(applicationContext, RegisterActivity::class.java).putExtra("address", address))
//                    finish()
//                }
//            }
//        }
//    }
//
//    private val chromeClient = object : WebChromeClient() {
//
//        override fun onCreateWindow(view: WebView?, isDialog: Boolean, isUserGesture: Boolean, resultMsg: Message?): Boolean {
//
//            val newWebView = WebView(this@AddressActivity)
//            newWebView.settings.javaScriptEnabled = true
//            val dialog = Dialog(this@AddressActivity)
//            dialog.setContentView(newWebView)
//
//            val params = dialog.window!!.attributes
//
//            params.width = ViewGroup.LayoutParams.MATCH_PARENT
//            params.height = ViewGroup.LayoutParams.MATCH_PARENT
//            dialog.window!!.attributes = params
//            dialog.show()
//
//            newWebView.webChromeClient = object : WebChromeClient() {
//                override fun onJsAlert(view: WebView, url: String, message: String, result: JsResult): Boolean {
//                    super.onJsAlert(view, url, message, result)
//                    return true
//                }
//                override fun onCloseWindow(window: WebView?) {
//                    dialog.dismiss()
//                }
//            }
//            (resultMsg!!.obj as WebView.WebViewTransport).webView = newWebView
//            resultMsg.sendToTarget()
//            return true
//        }
//    }
}