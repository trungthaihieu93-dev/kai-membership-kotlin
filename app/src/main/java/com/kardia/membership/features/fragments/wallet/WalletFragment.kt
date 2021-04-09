package com.kardia.membership.features.fragments.wallet

import android.os.Bundle
import com.kardia.membership.R
import com.kardia.membership.core.extension.*
import com.kardia.membership.core.platform.BaseFragment
import com.kardia.membership.data.entities.Transaction
import com.kardia.membership.data.entities.UserInfo
import com.kardia.membership.domain.entities.transaction.TransactionsEntity
import com.kardia.membership.domain.entities.user.UserInfoEntity
import com.kardia.membership.domain.usecases.tracking.PostTrackingActivityUseCase
import com.kardia.membership.features.utils.AppConstants
import com.kardia.membership.features.utils.DataConstants
import com.kardia.membership.features.viewmodel.TransactionViewModel
import com.kardia.membership.features.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_wallet.*

import javax.inject.Inject

class WalletFragment : BaseFragment() {

    @Inject
    lateinit var transactionAdapter: TransactionAdapter

    private lateinit var transactionViewModel: TransactionViewModel
    private lateinit var userViewModel: UserViewModel

    override fun layoutId(): Int {
        return R.layout.fragment_wallet
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        transactionViewModel = viewModel(viewModelFactory) {
            observe(transactionsEntity, ::onReceiveTransactionsEntity)
            observe(failureData, ::handleFailure)
        }
        userViewModel = viewModel(viewModelFactory) {
            observe(getUserInfoEntity, ::onReceiveUserInfoEntity)
        }
    }

    override fun initViews() {
        swipeRefreshLayout = srlWallet
        rvTransactionWallet.adapter = transactionAdapter
    }

    override fun initEvents() {
        fabReceive.setOnClickListener {
            if (isUserLogin) {
                mNavigator.showReceive(activity)
            } else {
                showLogin(activity)
            }
        }

        fabBuy.setOnClickListener {
            if (isUserLogin) {
                mNavigator.showBuy(activity)
            } else {
                showLogin(activity)
            }
        }

        fabSend.setOnClickListener {
            if (isUserLogin) {
                mNavigator.showSend(activity)
            } else {
                showLogin(activity)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        userInfoCache.get()?.let {
            setDataWallet(it)
        }
    }

    override fun loadData() {
        if (isUserLogin) {
            if (DataConstants.TRANSACTION_ENTITY == null) {
                transactionViewModel.getTransactions()
            } else {
                onReceiveTransactionsEntity(DataConstants.TRANSACTION_ENTITY)
            }
        } else {
            rlNoDataTransaction.visible()
        }
    }

    override fun reloadData() {
        transactionViewModel.getTransactions()
        userViewModel.getUserInfo()
    }

    private fun setDataWallet(userInfo: UserInfo) {
        userInfo.kai_info?.let { kai ->
            tvBalanceWallet.text = kai.wallet?.balance?.formatThousand()
            tvAddressWallet.text = kai.wallet?.wallet_address
            tvHolderWallet.text = String.format("%s%s", kai.first_name, kai.last_name)
        }
        userInfo.user_info?.let { user ->
            var createDate = user.createdDate?.changeDateFormat(
                "MM/yyyy"
            )
            createDate?.let {
                if (it.isEmpty()) {
                    createDate = user.createdDate?.changeDateFormat(
                        "MM/yyyy",
                        "yyyy-MM-dd'T'HH:mm:ss'Z'"
                    )
                }
            }
            tvMemberSinceWallet.text = createDate
        }

    }

    private fun onReceiveTransactionsEntity(entity: TransactionsEntity?) {
        hideProgress()
        DataConstants.TRANSACTION_ENTITY = entity
        sflTransaction.stopShimmer()
        sflTransaction.gone()
        rlNoDataTransaction.visible()
        llDataTransaction.gone()
        entity?.data?.let {
            rlNoDataTransaction.gone()
            llDataTransaction.visible()
            val transaction = Transaction("", it)
            val transactionList = ArrayList<Transaction>()
            transactionList.add(transaction)
            transactionAdapter.collection = transactionList
        }
    }

    private fun onReceiveUserInfoEntity(entity: UserInfoEntity?) {
        entity?.data?.let {
            userInfoCache.put(it)
            setDataWallet(it)
        }
    }
}