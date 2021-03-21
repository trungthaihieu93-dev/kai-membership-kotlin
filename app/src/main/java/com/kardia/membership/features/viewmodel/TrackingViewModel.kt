package com.kardia.membership.features.viewmodel

import androidx.lifecycle.MutableLiveData
import com.kardia.membership.core.platform.BaseViewModel
import com.kardia.membership.domain.entities.tracking.TrackingActivityEntity
import com.kardia.membership.domain.usecases.tracking.PostTrackingActivityUseCase
import javax.inject.Inject

class TrackingViewModel
@Inject constructor(
    private val postTrackingActivityUseCase: PostTrackingActivityUseCase
) : BaseViewModel() {
    var trackingActivityEntity: MutableLiveData<TrackingActivityEntity> = MutableLiveData()

    fun trackingActivity(params: PostTrackingActivityUseCase.Params) =
        postTrackingActivityUseCase(params) {
            it.fold(::handleFailure, ::handleTrackingActivity)
        }

    private fun handleTrackingActivity(data: TrackingActivityEntity) {
        this.trackingActivityEntity.value = data
    }
}