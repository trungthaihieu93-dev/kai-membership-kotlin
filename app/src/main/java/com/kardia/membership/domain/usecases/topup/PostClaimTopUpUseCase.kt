package com.kardia.membership.domain.usecases.topup

import com.kardia.membership.core.exception.Failure
import com.kardia.membership.core.functional.Either
import com.kardia.membership.core.interactor.UseCase
import com.kardia.membership.domain.entities.topup.ClaimTopUpEntity
import com.kardia.membership.domain.repositories.TopUpRepository
import javax.inject.Inject

class PostClaimTopUpUseCase
@Inject constructor(private val repository: TopUpRepository) :
    UseCase<ClaimTopUpEntity, PostClaimTopUpUseCase.Params>() {
    data class Params(
        val phone_number: String?,
        val provider_code: String?,
        val amount: Long?
    )

    override suspend fun run(params: Params): Either<Failure, ClaimTopUpEntity> {
        return repository.claimTopUp(params)
    }
}