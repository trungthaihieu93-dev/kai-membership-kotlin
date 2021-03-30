package com.kardia.membership.features.fragments.news

import android.annotation.SuppressLint
import android.net.http.SslError
import android.os.Bundle
import android.view.View
import android.webkit.*
import androidx.annotation.Nullable
import com.kardia.membership.R
import com.kardia.membership.core.extension.visible
import com.kardia.membership.core.platform.BaseBottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_sheet_detail_new.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class DetailNewsBottomSheet : BaseBottomSheetDialogFragment() {

    companion object {
        const val LINK_NEWS = "link"
    }

    private var link: String? = null
    private var callback: CallBack? = null

    override fun layoutId(): Int {
        return R.layout.bottom_sheet_detail_new
    }

    override fun isFullHeight(): Boolean {
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.let {
            link = it.getString(LINK_NEWS)
        }
        super.onViewCreated(view, savedInstanceState)
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun initViews() {
        ivClose.visible()
        wvDetailNews.settings.builtInZoomControls = true
        wvDetailNews.scrollBarStyle = WebView.SCROLLBARS_OUTSIDE_OVERLAY
        wvDetailNews.isScrollbarFadingEnabled = true
        wvDetailNews.settings.useWideViewPort = true
        wvDetailNews.settings.loadWithOverviewMode = true
        wvDetailNews.settings.javaScriptEnabled = true
        wvDetailNews.settings.allowFileAccess = true
        wvDetailNews.settings.cacheMode = WebSettings.LOAD_DEFAULT
        wvDetailNews.settings.setAppCachePath("/data/data/${activity?.packageName}/cache")
        wvDetailNews.settings.domStorageEnabled = true
        wvDetailNews.settings.allowFileAccess = true
        wvDetailNews.settings.setGeolocationEnabled(true)
        wvDetailNews.isSoundEffectsEnabled = true
        wvDetailNews.settings.setAppCacheEnabled(true)
        wvDetailNews.webChromeClient = WebChromeClient().apply {  }

        wvDetailNews.webViewClient = object : WebViewClient() {

            override fun shouldOverrideUrlLoading(
                view: WebView,
                request: WebResourceRequest
            ): Boolean {
                wvDetailNews.loadUrl(request.url.toString())
                return true
            }

            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                wvDetailNews.loadUrl(url)
                return true
            }

            @Nullable
            override fun shouldInterceptRequest(
                view: WebView,
                request: WebResourceRequest
            ): WebResourceResponse? { // isPost = request.getMethod().equalsIgnoreCase("POST");
                return super.shouldInterceptRequest(view, request)
            }

            override fun onPageFinished(view: WebView, url: String) {

            }
        }
//        if (headers.isNotEmpty()) {
//            webView.loadUrl(link, headers)
//        } else {
        link?.let { wvDetailNews.loadUrl(it) }
//        }
    }

    override fun initEvents() {
        ivClose.setOnClickListener {
            dismiss()
        }
    }

    override fun loadData() {
    }

    override fun reloadData() {

    }

    fun setCallBack(callback: CallBack?) {
        this.callback = callback
    }


    interface CallBack {
        fun onDismiss()
    }
}