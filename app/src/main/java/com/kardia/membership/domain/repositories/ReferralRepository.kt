package com.kardia.membership.domain.repositories

import com.kardia.membership.core.exception.Failure
import com.kardia.membership.core.functional.Either
import com.kardia.membership.core.platform.NetworkHandler
import com.kardia.membership.domain.entities.referral.ReferralEntity
import com.kardia.membership.domain.network.BaseNetwork
import com.kardia.membership.domain.services.ReferralService
import com.kardia.membership.domain.usecases.referral.PostReferralUseCase
import javax.inject.Inject

interface ReferralRepository {
    fun referral(params: PostReferralUseCase.Params): Either<Failure, ReferralEntity>

    class Network
    @Inject constructor(
        private val networkHandler: NetworkHandler,
        private val service: ReferralService
    ) : ReferralRepository, BaseNetwork() {
        override fun referral(params: PostReferralUseCase.Params): Either<Failure, ReferralEntity> {
            return when (networkHandler.isConnected) {
                true -> request(service.referral(params), {
                    it
                }, ReferralEntity.empty())
                false, null -> Either.Left(Failure.NetworkConnection)
            }
        }
    }
}