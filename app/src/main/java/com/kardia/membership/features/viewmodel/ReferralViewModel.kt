package com.kardia.membership.features.viewmodel

import androidx.lifecycle.MutableLiveData
import com.kardia.membership.core.platform.BaseViewModel
import com.kardia.membership.domain.entities.referral.ReferralEntity
import com.kardia.membership.domain.usecases.referral.PostReferralUseCase
import javax.inject.Inject

class ReferralViewModel
@Inject constructor(
    private val postReferralUseCase: PostReferralUseCase
) : BaseViewModel() {
    var referralEntity: MutableLiveData<ReferralEntity> = MutableLiveData()

    fun referral(params: PostReferralUseCase.Params) = postReferralUseCase(params) {
        it.fold(::handleFailure, ::handleDataReferral)
    }

    private fun handleDataReferral(data: ReferralEntity) {
        this.referralEntity.value = data
    }
}