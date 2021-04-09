package com.kardia.membership.features.fragments.send

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import com.google.zxing.integration.android.IntentIntegrator
import com.kardia.membership.R
import com.kardia.membership.core.extension.formatKAI
import com.kardia.membership.core.extension.gone
import com.kardia.membership.core.extension.visible
import com.kardia.membership.core.platform.BaseFragment
import com.kardia.membership.features.activities.ScanQRCodeActivity
import com.kardia.membership.features.viewmodel.QuestViewModel
import kotlinx.android.synthetic.main.fragment_send.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class SendFragment : BaseFragment() {
    private var address: String = ""
    private var amount: String = ""

    override fun layoutId() = R.layout.fragment_send

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun initViews() {
        ivClose.visible()

        etRecipientAddress.setText(address)
        etAmountSend.setText(amount)
    }

    override fun initEvents() {
        etRecipientAddress.addTextChangedListener {
            address = etRecipientAddress.text.toString().trim()
        }
        etAmountSend.addTextChangedListener {
            amount = etAmountSend.text.toString().trim()
        }

        ivClose.setOnClickListener {
            finish()
        }

        btContinue.setOnClickListener {
            tvMessageEmptyAddress.gone()
            tvMessageEmptyAmount.gone()
            if (address.isEmpty()) {
                tvMessageEmptyAddress.visible()
            }
            if (amount.isEmpty()) {
                tvMessageEmptyAmount.visible()
            }
            if (address.isNotEmpty() && amount.isNotEmpty()) {
                mNavigator.showSendOverview(
                    activity,
                    address,
                    amount
                )
            }
        }

        ivScan.setOnClickListener {
            val integrator = IntentIntegrator.forSupportFragment(this)
            integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)
            integrator.setPrompt("")
            integrator.setCameraId(0) // Use a specific camera of the device
            integrator.captureActivity = ScanQRCodeActivity::class.java
            integrator.setBeepEnabled(true)
            integrator.setOrientationLocked(false)
            integrator.setBarcodeImageEnabled(true)
            integrator.initiateScan()
        }
    }


    override fun loadData() {
        tvBalanceTopUp.text = userInfoCache.get()?.kai_info?.wallet?.balance?.formatKAI()
    }

    override fun reloadData() {

    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        val result =
            IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
//                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
            } else {
                etRecipientAddress.setText(result.contents)
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}