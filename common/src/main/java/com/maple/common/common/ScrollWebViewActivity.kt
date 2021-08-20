package com.maple.common.common

import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.nestedscrollwebview.NestedScrollWebView
import com.maple.common.R
import com.maple.common.base.BaseActivity

class ScrollWebViewActivity: BaseActivity(){
    private lateinit var webView: NestedScrollWebView
    private var webUrl: String = "https://www.baidu.com/"

    override fun getLayoutId(): Int = R.layout.activity_scroll_webview

    override fun initData(savedInstanceState: Bundle?) {
        setTitle<ScrollWebViewActivity>("WebView").onBack<ScrollWebViewActivity> {
            onFinish()
        }
        webView = findViewById(R.id.webView)

        webView.settings.apply {
            // 开启 localStorage
            domStorageEnabled = true
            // 设置支持javascript
            javaScriptEnabled = true
            // 启动缓存
            setAppCacheEnabled(true)
            // 设置缓存模式
            cacheMode = WebSettings.LOAD_DEFAULT
            // 使用自定义的WebViewClient
        }

        webView.apply {
            //使用自定义的WebViewClient
            webViewClient = object:  WebViewClient(){
                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?
                ): Boolean {
                    //return super.shouldOverrideUrlLoading(view, request)
                    view?.loadUrl(webUrl)
                    return true
                }
            }
        }
//        webView.loadUrl("https://www.baidu.com/#iact=wiseindex%2Ftabs%2Fnews%2Factivity%2Fnewsdetail%3D%257B%2522linkData%2522%253A%257B%2522name%2522%253A%2522iframe%252Fmib-iframe%2522%252C%2522id%2522%253A%2522feed%2522%252C%2522index%2522%253A0%252C%2522url%2522%253A%2522https%253A%252F%252Fmbd.baidu.com%252Fnewspage%252Fdata%252Flandingpage%253Fs_type%253Dnews%2526dsp%253Dwise%2526context%253D%25257B%252522nid%252522%25253A%252522news_10679415583042193768%252522%25257D%2526pageType%253D1%2526n_type%253D1%2526p_from%253D-1%2526innerIframe%253D1%2522%252C%2522isThird%2522%253Afalse%252C%2522title%2522%253Anull%257D%257D");
        webView.loadUrl(webUrl)
    }
}