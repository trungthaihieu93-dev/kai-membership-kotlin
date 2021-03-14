package com.kardia.membership.features.fragments.top_up

import android.graphics.Color
import android.os.Bundle
import android.view.View
import com.kardia.membership.R
import com.kardia.membership.core.extension.formatKAI
import com.kardia.membership.core.extension.formatPrice
import com.kardia.membership.core.extension.gone
import com.kardia.membership.core.extension.visible
import com.kardia.membership.core.platform.BaseFragment
import com.kardia.membership.data.entities.ConfigItem
import com.kardia.membership.data.entities.TopUpAmount
import com.kardia.membership.features.utils.AppConstants
import com.kardia.membership.features.utils.DataConstants
import kotlinx.android.synthetic.main.fragment_topup.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class TopUpFragment : BaseFragment() {
    private var providerCode: String? = null
    private var topUpAmount: TopUpAmount? = null
    private var positionProviderCode = 1
    private var positionTopUpAmount = 0
    private var phone: String = ""
    override fun layoutId() = R.layout.fragment_topup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun initViews() {
        ivBack.visible()

        handleChooseServiceProvider(positionProviderCode)
        etPhoneTopUp.setText(phone)
    }

    override fun initEvents() {
        ivBack.setOnClickListener {
            finish()
        }
        mcvViettel.setOnClickListener {
            handleChooseServiceProvider(1)
        }
        mcvMobifone.setOnClickListener {
            handleChooseServiceProvider(2)
        }
        mcvVinaphone.setOnClickListener {
            handleChooseServiceProvider(3)
        }
        llTopUpAmount.setOnClickListener {
            val callback = object : TopUpAmountBottomSheet.CallBack {
                override fun onItemClick(topUpAmount: TopUpAmount, position: Int) {
                    positionTopUpAmount = position
                    setTextAmount(topUpAmount)
                }
            }
            mNavigator.showTopUpAmount(activity, callback)
        }
        btContinue.setOnClickListener {
            tvMessageEmailPhone.gone()
            phone = etPhoneTopUp.text.toString().trim()
            if (phone.isNotEmpty()) {
                mNavigator.showOverview(
                    activity,
                    etPhoneTopUp.text.toString().trim(),
                    providerCode,
                    topUpAmount
                )
            } else {
                tvMessageEmailPhone.visible()
            }
        }
    }

    override fun loadData() {
        tvBalanceTopUp.text = userInfoCache.get()?.kai_info?.wallet?.balance?.formatKAI()
        configCache.get()?.data?.let { configList ->
            var amountList = ArrayList<ConfigItem>()
            var exchangeKAIList = ArrayList<ConfigItem>()
            val topUpAmountList = ArrayList<TopUpAmount>()
            configList.forEach {
                when (it.group_name) {
                    AppConstants.GROUP_NAME_AMOUNT -> amountList =
                        it.config as ArrayList<ConfigItem>
                    AppConstants.GROUP_NAME_EXCHANGE_KAI -> exchangeKAIList =
                        it.config as ArrayList<ConfigItem>
                }
            }
            amountList.forEach { amount ->
                exchangeKAIList.forEach { exchange ->
                    if (amount.key == exchange.key) {
                        topUpAmountList.add(TopUpAmount(amount.key, amount.value, exchange.value))
                    }
                }
            }
            if (topUpAmountList.isNotEmpty()) {
                topUpAmountList[positionTopUpAmount].isSelected = true
                setTextAmount(topUpAmountList[positionTopUpAmount])

                DataConstants.TOP_UP_AMOUNT_LIST = topUpAmountList
            }
        }
    }

    override fun reloadData() {

    }

    private fun handleChooseServiceProvider(position: Int) {
        positionProviderCode = position
        mcvViettel.strokeColor = Color.WHITE
        mcvMobifone.strokeColor = Color.WHITE
        mcvVinaphone.strokeColor = Color.WHITE
        when (position) {
            1 -> {
                providerCode = "Viettel"
                mcvViettel.strokeColor = getColor(R.color.color_0A1F44)
            }
            2 -> {
                providerCode = "Mobifone"
                mcvMobifone.strokeColor = getColor(R.color.color_0A1F44)
            }
            3 -> {
                providerCode = "Vinaphone"
                mcvVinaphone.strokeColor = getColor(R.color.color_0A1F44)
            }
        }
        mcvViettel.invalidate()
        mcvMobifone.invalidate()
        mcvVinaphone.invalidate()
    }

    private fun setTextAmount(topUpAmount: TopUpAmount) {
        tvAmountTopUp.text = topUpAmount.priceValue?.formatPrice()
        tvExchangeKAITopUp.text = topUpAmount.KAIValue

        this.topUpAmount = topUpAmount
    }
}