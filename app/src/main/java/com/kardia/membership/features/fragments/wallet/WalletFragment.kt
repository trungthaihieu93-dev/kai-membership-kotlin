package com.kardia.membership.features.fragments.wallet

import android.os.Bundle
import com.kardia.membership.R
import com.kardia.membership.core.extension.*
import com.kardia.membership.core.platform.BaseFragment
import com.kardia.membership.data.entities.Transaction
import com.kardia.membership.data.entities.UserInfo
import com.kardia.membership.domain.entities.transaction.TransactionsEntity
import com.kardia.membership.features.utils.DataConstants
import com.kardia.membership.features.viewmodel.TransactionViewModel
import kotlinx.android.synthetic.main.fragment_wallet.*

import javax.inject.Inject

class WalletFragment : BaseFragment() {

    @Inject
    lateinit var transactionAdapter: TransactionAdapter

    private lateinit var transactionViewModel: TransactionViewModel


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
    }

    override fun initViews() {
        rvTransactionWallet.adapter = transactionAdapter
    }

    override fun initEvents() {
        fabReceive.setOnClickListener {
            if (isUserLogin) {
                mNavigator.showReceive(activity)
            } else {
                mNavigator.showLogin(activity)
            }
        }

        fabBuy.setOnClickListener {
            if (isUserLogin) {
                mNavigator.showBuy(activity)
            } else {
                mNavigator.showLogin(activity)
            }
        }
    }

    override fun loadData() {
        if (isUserLogin) {
            userInfoCache.get()?.let {
                setDataWallet(it)
            }
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
    }

    private fun setDataWallet(userInfo: UserInfo) {
        userInfo.kai_info?.let { kai ->
            tvBalanceWallet.text = kai.wallet?.balance?.formatThousand()
            tvAddressWallet.text = kai.wallet?.wallet_address
            tvHolderWallet.text = kai.first_name
        }
        userInfo.user_info?.let { user ->
            tvMemberSinceWallet.text = user.createdDate?.changeDateFormat(
                "MM/yyyy"
            )
        }

    }

    private fun onReceiveTransactionsEntity(entity: TransactionsEntity?) {
        hideProgress()
        DataConstants.TRANSACTION_ENTITY = entity
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
}