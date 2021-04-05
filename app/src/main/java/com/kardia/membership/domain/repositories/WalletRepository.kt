package com.kardia.membership.domain.repositories

import com.kardia.membership.core.exception.Failure
import com.kardia.membership.core.functional.Either
import com.kardia.membership.core.platform.NetworkHandler
import com.kardia.membership.domain.entities.wallet.SendKAIEntity
import com.kardia.membership.domain.network.BaseNetwork
import com.kardia.membership.domain.services.WalletService
import com.kardia.membership.domain.usecases.wallet.PostSendKAIUseCase
import javax.inject.Inject

interface WalletRepository {
    fun sendKAI(params: PostSendKAIUseCase.Params): Either<Failure, SendKAIEntity>

    class Network
    @Inject constructor(
        private val networkHandler: NetworkHandler,
        private val service: WalletService
    ) : WalletRepository, BaseNetwork() {
        override fun sendKAI(params: PostSendKAIUseCase.Params): Either<Failure, SendKAIEntity> {
            return when (networkHandler.isConnected) {
                true -> request(service.sendKAI(params), {
                    it
                }, SendKAIEntity.empty())
                false, null -> Either.Left(Failure.NetworkConnection)
            }
        }
    }
}