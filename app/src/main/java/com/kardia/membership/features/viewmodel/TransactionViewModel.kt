package com.kardia.membership.features.viewmodel

import androidx.lifecycle.MutableLiveData
import com.kardia.membership.core.platform.BaseViewModel
import com.kardia.membership.domain.entities.device.PasscodeDeviceEntity
import com.kardia.membership.domain.entities.transaction.TransactionsEntity
import com.kardia.membership.domain.usecases.transaction.GetTransactionsUseCase
import javax.inject.Inject

class TransactionViewModel
@Inject constructor(
    private val getTransactionsUseCase: GetTransactionsUseCase
) : BaseViewModel() {
    var transactionsEntity: MutableLiveData<TransactionsEntity> = MutableLiveData()

    fun getTransactions() = getTransactionsUseCase("") {
        it.fold(::handleFailure, ::handleTransactionsEntity)
    }

    private fun handleTransactionsEntity(data: TransactionsEntity) {
        this.transactionsEntity.value = data
    }
}