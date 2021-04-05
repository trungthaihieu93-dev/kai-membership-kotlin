package com.kardia.membership.features.fragments.send_overview

import android.os.Bundle
import android.view.View
import com.kardia.membership.R
import com.kardia.membership.core.exception.Failure
import com.kardia.membership.core.extension.*
import com.kardia.membership.core.platform.BaseFragment
import com.kardia.membership.domain.entities.topup.ClaimTopUpEntity
import com.kardia.membership.domain.entities.user.UserInfoEntity
import com.kardia.membership.domain.entities.wallet.SendKAIEntity
import com.kardia.membership.domain.usecases.topup.PostClaimTopUpUseCase
import com.kardia.membership.domain.usecases.wallet.PostSendKAIUseCase
import com.kardia.membership.features.fragments.top_up_overview.ClaimTopUpSuccessBottomSheet
import com.kardia.membership.features.viewmodel.TopUpViewModel
import com.kardia.membership.features.viewmodel.UserViewModel
import com.kardia.membership.features.viewmodel.WalletViewModel
import kotlinx.android.synthetic.main.fragment_send.*
import kotlinx.android.synthetic.main.fragment_send_overview.*
import kotlinx.android.synthetic.main.fragment_send_overview.etAmountSend
import kotlinx.android.synthetic.main.fragment_send_overview.etRecipientAddress
import kotlinx.android.synthetic.main.fragment_send_overview.tvBalanceTopUp
import kotlinx.android.synthetic.main.layout_toolbar.*

class SendOverviewFragment : BaseFragment() {
    private var address: String? = null
    private var amount: String? = null

    companion object {
        const val ADDRESS = "address"
        const val AMOUNT = "amount"
    }

    private lateinit var walletViewModel: WalletViewModel
    private lateinit var userViewModel: UserViewModel
    override fun layoutId() = R.layout.fragment_send_overview

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        walletViewModel = viewModel(viewModelFactory) {
            observe(sendKAIEntity, ::onReceiveSendKAIEntity)
            observe(failureData, ::handleFailure)
        }
        userViewModel = viewModel(viewModelFactory) {
            observe(getUserInfoEntity, ::onReceiveUserInfoEntity)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        address = arguments?.getString(ADDRESS)
        amount = arguments?.getString(AMOUNT)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun initViews() {
        ivBack.visible()
    }

    override fun initEvents() {
        btSendNow.setOnClickListener {
            showProgress()
            walletViewModel.sendKAI(PostSendKAIUseCase.Params(address, amount?.toFloatOrNull()))
        }

        ivBack.setOnClickListener {
            close()
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

            override fun onCheckAgain() {

            }
        }
        mNavigator.showSendKaiFail(activity, callback)
    }

    private fun onReceiveSendKAIEntity(entity: SendKAIEntity?) {
        hideProgress()
        userViewModel.getUserInfo()
        val callback = object : SendKaiSuccessBottomSheet.CallBack {
            override fun onDismiss() {
                close()
            }

            override fun onBackToMyWallet() {
                finish()
            }
        }
        mNavigator.showSendKaiSuccess(activity, callback)
    }

    private fun onReceiveUserInfoEntity(entity: UserInfoEntity?) {
        entity?.data?.let {
            userInfoCache.put(it)
        }
    }

}