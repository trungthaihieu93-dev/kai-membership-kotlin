package com.kardia.membership.features.viewmodel

import androidx.lifecycle.MutableLiveData
import com.kardia.membership.core.platform.BaseViewModel
import com.kardia.membership.domain.entities.wallet.SendKAIEntity
import com.kardia.membership.domain.usecases.wallet.PostSendKAIUseCase
import javax.inject.Inject

class WalletViewModel
@Inject constructor(
    private val postSendKAIUseCase: PostSendKAIUseCase
) : BaseViewModel() {
    var sendKAIEntity: MutableLiveData<SendKAIEntity> = MutableLiveData()

    fun sendKAI(params:PostSendKAIUseCase.Params) = postSendKAIUseCase(params) {
        it.fold(::handleFailure, ::handleDataSendKAI)
    }

    private fun handleDataSendKAI(data: SendKAIEntity) {
        this.sendKAIEntity.value = data
    }
}