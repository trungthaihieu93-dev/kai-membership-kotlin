package com.kardia.membership.domain.usecases.passcode

import com.kardia.membership.core.exception.Failure
import com.kardia.membership.core.functional.Either
import com.kardia.membership.core.interactor.UseCase
import com.kardia.membership.domain.entities.passcode.LoginPasscodeEntity
import com.kardia.membership.domain.entities.passcode.RegisterPasscodeEntity
import com.kardia.membership.domain.repositories.PasscodeRepository
import javax.inject.Inject

class PostLoginPasscodeUseCase
@Inject constructor(private val repository: PasscodeRepository) :
    UseCase<LoginPasscodeEntity, PostLoginPasscodeUseCase.Params>() {
    data class Params(
        val passcode: String,
        val email: String?,
        val device_id: String,
        val os: String
    )

    override suspend fun run(params: Params): Either<Failure, LoginPasscodeEntity> {
        return repository.login(params)
    }
}