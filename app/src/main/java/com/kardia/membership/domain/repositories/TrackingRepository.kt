package com.kardia.membership.domain.repositories

import com.kardia.membership.core.exception.Failure
import com.kardia.membership.core.functional.Either
import com.kardia.membership.core.platform.NetworkHandler
import com.kardia.membership.domain.entities.tracking.TrackingActivityEntity
import com.kardia.membership.domain.network.BaseNetwork
import com.kardia.membership.domain.services.TrackingService
import com.kardia.membership.domain.usecases.tracking.PostTrackingActivityUseCase
import javax.inject.Inject

interface TrackingRepository {
    fun trackingActivity(params :PostTrackingActivityUseCase.Params): Either<Failure, TrackingActivityEntity>

    class Network
    @Inject constructor(
        private val networkHandler: NetworkHandler,
        private val service: TrackingService
    ) : TrackingRepository, BaseNetwork() {
        override fun trackingActivity(params: PostTrackingActivityUseCase.Params): Either<Failure, TrackingActivityEntity> {
            return when (networkHandler.isConnected) {
                true -> request(service.trackingActivity(params), {
                    it
                }, TrackingActivityEntity.empty())
                false, null -> Either.Left(Failure.NetworkConnection)
            }
        }
    }
}