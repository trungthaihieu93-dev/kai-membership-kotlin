package com.kardia.membership.features.viewmodel

import androidx.lifecycle.MutableLiveData
import com.kardia.membership.core.platform.BaseViewModel
import com.kardia.membership.domain.entities.config.ConfigEntity
import com.kardia.membership.domain.entities.topup.ClaimTopUpEntity
import com.kardia.membership.domain.usecases.topup.PostClaimTopUpUseCase
import javax.inject.Inject

class TopUpViewModel
@Inject constructor(
    private val postClaimTopUpUseCase: PostClaimTopUpUseCase
) : BaseViewModel() {
    var claimTopUpEntity: MutableLiveData<ClaimTopUpEntity> = MutableLiveData()

    fun claimTopUp(params: PostClaimTopUpUseCase.Params) = postClaimTopUpUseCase(params) {
        it.fold(::handleFailure, ::handleClaimTopUp)
    }

    private fun handleClaimTopUp(data: ClaimTopUpEntity) {
        this.claimTopUpEntity.value = data
    }
}