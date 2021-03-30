package com.kardia.membership.domain.usecases.passcode

import com.kardia.membership.core.exception.Failure
import com.kardia.membership.core.functional.Either
import com.kardia.membership.core.interactor.UseCase
import com.kardia.membership.domain.entities.passcode.ResetPasscodeEntity
import com.kardia.membership.domain.repositories.PasscodeRepository
import javax.inject.Inject

class PostResetPasscodeUseCase
@Inject constructor(private val repository: PasscodeRepository) :
    UseCase<ResetPasscodeEntity, PostResetPasscodeUseCase.Params>() {
    data class Params(val mobile_unique_id: String?, val passcode: String?, val token: String?, val email: String?)

    override suspend fun run(params: Params): Either<Failure, ResetPasscodeEntity> {
        return repository.reset(params)
    }
}