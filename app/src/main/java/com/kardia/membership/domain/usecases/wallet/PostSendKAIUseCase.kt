package com.kardia.membership.domain.usecases.wallet

import com.kardia.membership.core.exception.Failure
import com.kardia.membership.core.functional.Either
import com.kardia.membership.core.interactor.UseCase
import com.kardia.membership.domain.entities.wallet.SendKAIEntity
import com.kardia.membership.domain.repositories.WalletRepository
import javax.inject.Inject

class PostSendKAIUseCase
@Inject constructor(private val repository: WalletRepository) :
    UseCase<SendKAIEntity, PostSendKAIUseCase.Params>() {
    data class Params(
        val wallet_address: String?,
        val amount: Float?
    )

    override suspend fun run(params: Params): Either<Failure, SendKAIEntity> {
        return repository.sendKAI(params)
    }
}