package com.kardia.membership.features.fragments.receive

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.kardia.membership.R
import com.kardia.membership.core.extension.close
import com.kardia.membership.core.extension.visible
import com.kardia.membership.core.platform.BaseFragment
import kotlinx.android.synthetic.main.fragment_receive.*
import kotlinx.android.synthetic.main.fragment_receive.ivCopy
import kotlinx.android.synthetic.main.fragment_receive.ivQRCodeWalletAddressReceive
import kotlinx.android.synthetic.main.fragment_receive.tvWalletAddressReceive
import kotlinx.android.synthetic.main.fragment_wallet.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class ReceiveFragment : BaseFragment() {

    override fun layoutId() = R.layout.fragment_receive

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun initViews() {
        ivClose.visible()
    }

    override fun initEvents() {
        ivClose.setOnClickListener {
            finish()
        }

        ivCopy.setOnClickListener {
            userInfoCache.get()?.kai_info?.wallet?.wallet_address?.let {
                copyTextToClipboard(it)
            }
        }

        llShareWalletAddress.setOnClickListener {
            userInfoCache.get()?.kai_info?.wallet?.wallet_address?.let {
                val sendIntent = Intent()
                sendIntent.action = Intent.ACTION_SEND
                sendIntent.putExtra(
                    Intent.EXTRA_TEXT,
                    it
                )
                sendIntent.type = "text/plain"
                startActivity(sendIntent)
            }
        }
    }

    override fun loadData() {
        try {
            userInfoCache.get()?.kai_info?.wallet?.wallet_address?.let {
                val barcodeEncoder = BarcodeEncoder()
                val bitmap = barcodeEncoder.encodeBitmap(it, BarcodeFormat.QR_CODE, 800, 800)
                ivQRCodeWalletAddressReceive.setImageBitmap(bitmap)
                tvWalletAddressReceive.text = it
            }
        } catch (e: Exception) {
        }
    }

    override fun reloadData() {

    }

}