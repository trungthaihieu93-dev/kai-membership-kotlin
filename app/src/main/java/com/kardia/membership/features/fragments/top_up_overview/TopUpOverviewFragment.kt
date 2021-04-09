package com.kardia.membership.features.fragments.top_up_overview

import android.os.Bundle
import android.view.View
import com.kardia.membership.R
import com.kardia.membership.core.exception.Failure
import com.kardia.membership.core.extension.*
import com.kardia.membership.core.platform.BaseFragment
import com.kardia.membership.data.entities.TopUpAmount
import com.kardia.membership.domain.entities.topup.ClaimTopUpEntity
import com.kardia.membership.domain.usecases.topup.PostClaimTopUpUseCase
import com.kardia.membership.features.utils.TrackingUtil
import com.kardia.membership.features.viewmodel.TopUpViewModel
import kotlinx.android.synthetic.main.fragment_topup_overview.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class TopUpOverviewFragment : BaseFragment() {
    private var phone: String? = null
    private var providerCode: String? = null
    private var topUpAmount: TopUpAmount? = null

    private lateinit var topUpViewModel: TopUpViewModel

    companion object {
        const val PHONE = "phone"
        const val PROVIDER_CODE = "providerCode"
        const val TOP_UP_AMOUNT = "topUpAmount"
    }

    override fun layoutId() = R.layout.fragment_topup_overview

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        topUpViewModel = viewModel(viewModelFactory) {
            observe(claimTopUpEntity, ::onReceiveClaimTopUpEntity)
            observe(failureData, ::handleFailure)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        phone = arguments?.getString(PHONE)
        providerCode = arguments?.getString(PROVIDER_CODE)
        topUpAmount = arguments?.getParcelable(TOP_UP_AMOUNT)

        super.onViewCreated(view, savedInstanceState)
    }

    override fun initViews() {
        ivBack.visible()
    }

    override fun initEvents() {
        ivBack.setOnClickListener {
            close()
        }

        btTopUpNow.setOnClickListener {
            showProgress()
            topUpViewModel.claimTopUp(
                PostClaimTopUpUseCase.Params(
                    phone,
                    providerCode,
                    topUpAmount?.priceValue?.toLong()
                )
            )
        }
    }

    override fun loadData() {
        tvBalanceOverview.text = userInfoCache.get()?.kai_info?.wallet?.balance?.formatKAI()
        etPhoneOverview.setText(phone)
        etProviderCodeOverview.setText(providerCode)
        tvAmountOverview.text = topUpAmount?.priceValue?.formatPrice()
        tvExchangeKAIOverview.text = topUpAmount?.KAIValue
    }

    override fun reloadData() {

    }

    private fun onReceiveClaimTopUpEntity(entity: ClaimTopUpEntity?) {
        hideProgress()
        val callback = object : ClaimTopUpSuccessBottomSheet.CallBack {
            override fun onDismiss() {
//                close()
            }

            override fun onBackToUtilities() {
                finish()
            }
        }
        mNavigator.showClaimTopUpSuccess(activity, callback)
        tracking(TrackingUtil.TOP_UP,userInfoCache.get()?.user_info)
    }

    override fun handleFailure(failure: Failure?) {
        hideProgress()
        val callback = object : ClaimTopUpFailBottomSheet.CallBack {
            override fun onDismiss() {

            }
        }
        mNavigator.showClaimTopUpFail(activity, callback)
    }

}