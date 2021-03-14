package com.kardia.membership.domain.repositories

import com.kardia.membership.core.exception.Failure
import com.kardia.membership.core.functional.Either
import com.kardia.membership.core.platform.NetworkHandler
import com.kardia.membership.domain.entities.topup.ClaimTopUpEntity
import com.kardia.membership.domain.network.BaseNetwork
import com.kardia.membership.domain.services.TopUpService
import com.kardia.membership.domain.usecases.topup.PostClaimTopUpUseCase
import javax.inject.Inject

interface TopUpRepository {
    fun claimTopUp(params: PostClaimTopUpUseCase.Params): Either<Failure, ClaimTopUpEntity>

    class Network
    @Inject constructor(
        private val networkHandler: NetworkHandler,
        private val service: TopUpService
    ) : TopUpRepository, BaseNetwork() {
        override fun claimTopUp(params: PostClaimTopUpUseCase.Params): Either<Failure, ClaimTopUpEntity> {
            return when (networkHandler.isConnected) {
                true -> request(service.claimTopUp(params), {
                    it
                }, ClaimTopUpEntity.empty())
                false, null -> Either.Left(Failure.NetworkConnection)
            }
        }

    }
}