package com.kardia.membership.features.fragments.buy

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import com.kardia.membership.R
import com.kardia.membership.core.extension.gone
import com.kardia.membership.core.extension.visible
import com.kardia.membership.core.platform.BaseFragment
import com.kardia.membership.data.cache.ConfigCache
import com.kardia.membership.features.utils.AppConstants
import kotlinx.android.synthetic.main.fragment_buy.*
import kotlinx.android.synthetic.main.layout_toolbar.*


class BuyFragment : BaseFragment() {
    private var linkBuy =""
    override fun layoutId() = R.layout.fragment_buy

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun initViews() {
        ivClose.visible()
        btOpenNami.gone()
        btDownloadNami.gone()

        configCache.get()?.data?.forEach { config ->
            if(config.group_name == AppConstants.GROUP_NAME_LINK_BUY){
                config.config?.forEach { configItem->
                    if(configItem.key == AppConstants.DEVICE_OS){
                        configItem.value?.let{value ->
                            linkBuy = value.substringAfterLast("?id=")
                        }
                    }
                }
            }
        }
        context?.let {
            val pm = it.packageManager
            val isInstalled = isPackageInstalled(linkBuy, pm)
            if (isInstalled) {
                btOpenNami.visible()
            } else {
                btDownloadNami.visible()
            }
        }
    }

    override fun initEvents() {
        ivClose.setOnClickListener {
            finish()
        }

        btOpenNami.setOnClickListener {
            val launchIntent: Intent? = activity?.packageManager?.getLaunchIntentForPackage(linkBuy)
            launchIntent?.let { startActivity(it) }
        }

        btDownloadNami.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(
                    "https://play.google.com/store/apps/details?id=$linkBuy"
                )
                setPackage("com.android.vending")
            }
            startActivity(intent)
        }
    }

    override fun loadData() {
    }

    override fun reloadData() {

    }

    private fun isPackageInstalled(
        packageName: String,
        packageManager: PackageManager
    ): Boolean {
        return try {
            packageManager.getPackageInfo(packageName, 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }
}