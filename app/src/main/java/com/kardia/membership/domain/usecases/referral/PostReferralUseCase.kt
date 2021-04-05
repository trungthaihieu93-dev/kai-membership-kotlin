package com.kardia.membership.domain.usecases.referral

import com.kardia.membership.core.exception.Failure
import com.kardia.membership.core.functional.Either
import com.kardia.membership.core.interactor.UseCase
import com.kardia.membership.domain.entities.referral.ReferralEntity
import com.kardia.membership.domain.repositories.ReferralRepository
import javax.inject.Inject

class PostReferralUseCase
@Inject constructor(private val repository: ReferralRepository) :
    UseCase<ReferralEntity, PostReferralUseCase.Params>() {
    data class Params(
        val code_user: String?
    )

    override suspend fun run(params: Params): Either<Failure, ReferralEntity> {
        return repository.referral(params)
    }
}