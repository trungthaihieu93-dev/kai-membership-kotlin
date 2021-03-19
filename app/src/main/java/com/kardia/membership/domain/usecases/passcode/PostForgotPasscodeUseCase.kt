package com.kardia.membership.domain.usecases.passcode

import com.kardia.membership.core.exception.Failure
import com.kardia.membership.core.functional.Either
import com.kardia.membership.core.interactor.UseCase
import com.kardia.membership.domain.entities.passcode.ForgotPasscodeEntity
import com.kardia.membership.domain.repositories.PasscodeRepository
import javax.inject.Inject

class PostForgotPasscodeUseCase
@Inject constructor(private val repository: PasscodeRepository) :
    UseCase<ForgotPasscodeEntity, PostForgotPasscodeUseCase.Params>() {
    data class Params(
        val email: String,
        val mobile_unique_id: String?
    )

    override suspend fun run(params: Params): Either<Failure, ForgotPasscodeEntity> {
        return repository.forgot(params)
    }
}