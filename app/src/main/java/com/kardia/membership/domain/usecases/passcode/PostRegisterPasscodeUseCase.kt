package com.kardia.membership.domain.usecases.passcode

import com.kardia.membership.core.exception.Failure
import com.kardia.membership.core.functional.Either
import com.kardia.membership.core.interactor.UseCase
import com.kardia.membership.domain.entities.auth.RegisterAuthEntity
import com.kardia.membership.domain.entities.passcode.RegisterPasscodeEntity
import com.kardia.membership.domain.repositories.AuthRepository
import com.kardia.membership.domain.repositories.PasscodeRepository
import javax.inject.Inject

class PostRegisterPasscodeUseCase
@Inject constructor(private val repository: PasscodeRepository) :
    UseCase<RegisterPasscodeEntity, PostRegisterPasscodeUseCase.Params>() {
    data class Params(val device_id: String?, val passcode: String?, val refresh_token: String?, val email: String?, val os: String?)

    override suspend fun run(params: Params): Either<Failure, RegisterPasscodeEntity> {
        return repository.register(params)
    }
}