package com.kardia.membership.features.fragments.send_overview

import android.os.Bundle
import android.view.View
import com.kardia.membership.R
import com.kardia.membership.core.exception.Failure
import com.kardia.membership.core.extension.formatKAI
import com.kardia.membership.core.platform.BaseFragment
import kotlinx.android.synthetic.main.fragment_send.*
import kotlinx.android.synthetic.main.fragment_send_overview.*
import kotlinx.android.synthetic.main.fragment_send_overview.etAmountSend
import kotlinx.android.synthetic.main.fragment_send_overview.etRecipientAddress
import kotlinx.android.synthetic.main.fragment_send_overview.tvBalanceTopUp

class SendOverviewFragment : BaseFragment() {
    private var address: String? = null
    private var amount: String? = null

    companion object {
        const val ADDRESS = "address"
        const val AMOUNT = "amount"
    }

    override fun layoutId() = R.layout.fragment_send_overview

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        address = arguments?.getString(ADDRESS)
        amount = arguments?.getString(AMOUNT)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun initViews() {

    }

    override fun initEvents() {
        btSendNow.setOnClickListener {
            val callback = object : SendKaiSuccessBottomSheet.CallBack {
                override fun onDismiss() {
                    finish()
                }
            }
            mNavigator.showSendKaiSuccess(activity, callback)
        }
    }

    override fun loadData() {
        tvBalanceTopUp.text = userInfoCache.get()?.kai_info?.wallet?.balance?.formatKAI()

        etRecipientAddress.setText(address)
        etAmountSend.setText(amount)
    }

    override fun reloadData() {

    }

    override fun handleFailure(failure: Failure?) {
        hideProgress()
        val callback = object : SendKaiFailBottomSheet.CallBack {
            override fun onDismiss() {

            }
        }
        mNavigator.showSendKaiFail(activity, callback)
    }

}