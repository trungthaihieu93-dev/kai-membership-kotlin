package com.kardia.membership.features.fragments.spin

import android.os.Bundle
import android.webkit.*
import android.webkit.WebView
import androidx.annotation.Nullable
import com.kardia.membership.R
import com.kardia.membership.core.extension.visible
import com.kardia.membership.core.platform.BaseFragment
import com.kardia.membership.features.utils.AppConstants
import kotlinx.android.synthetic.main.fragment_spin.*

class SpinFragment : BaseFragment() {

    override fun layoutId() = R.layout.fragment_spin

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
    }

    override fun initViews() {
    }

    override fun initEvents() {
        ivClose.setOnClickListener {
            finish()
        }
    }

    override fun loadData() {
        wvSpin.settings.builtInZoomControls = true
        wvSpin.scrollBarStyle = WebView.SCROLLBARS_OUTSIDE_OVERLAY
        wvSpin.isScrollbarFadingEnabled = true
        wvSpin.settings.useWideViewPort = true
        wvSpin.settings.loadWithOverviewMode = true
        wvSpin.settings.javaScriptEnabled = true
        wvSpin.settings.allowFileAccess = true
        wvSpin.settings.cacheMode = WebSettings.LOAD_DEFAULT
        wvSpin.settings.setAppCachePath("/data/data/${activity?.packageName}/cache")
        wvSpin.settings.domStorageEnabled = true
        wvSpin.settings.allowFileAccess = true
        wvSpin.settings.setGeolocationEnabled(true)
        wvSpin.isSoundEffectsEnabled = true
        wvSpin.settings.setAppCacheEnabled(true)
        wvSpin.webChromeClient = WebChromeClient()
        wvSpin.webViewClient = object : WebViewClient() {

            override fun shouldOverrideUrlLoading(
                view: WebView,
                request: WebResourceRequest
            ): Boolean {
                wvSpin.loadUrl(request.url.toString())
                return true
            }

            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                wvSpin.loadUrl(url)
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
        val link = String.format(
            "https://game-003-tego022.bcms.tech?token=%s&lang=%s&device=%s&platform=%s",
            userTokenCache.get()?.accessToken,
            "vi",
            AppConstants.DEVICE_ID_TEST,
            AppConstants.DEVICE_OS.toLowerCase()
        )
//        if (headers.isNotEmpty()) {
//            wvSpin.loadUrl(link, headers)
//        } else {
            wvSpin.loadUrl(link)
//        }
    }

    override fun reloadData() {

    }
}