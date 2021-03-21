package com.kardia.membership.domain.usecases.tracking

import com.kardia.membership.core.exception.Failure
import com.kardia.membership.core.functional.Either
import com.kardia.membership.core.interactor.UseCase
import com.kardia.membership.domain.entities.tracking.TrackingActivityEntity
import com.kardia.membership.domain.repositories.TrackingRepository
import javax.inject.Inject

class PostTrackingActivityUseCase
@Inject constructor(private val repository: TrackingRepository) :
    UseCase<TrackingActivityEntity, PostTrackingActivityUseCase.Params>() {
    data class Params(
        val user_id: String?,
        val device_id: String?
    )

    override suspend fun run(params: Params): Either<Failure, TrackingActivityEntity> {
        return repository.trackingActivity(params)
    }
}