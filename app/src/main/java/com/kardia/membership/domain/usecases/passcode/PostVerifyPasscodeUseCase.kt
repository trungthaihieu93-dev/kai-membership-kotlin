package com.kardia.membership.domain.usecases.passcode

import com.kardia.membership.core.exception.Failure
import com.kardia.membership.core.functional.Either
import com.kardia.membership.core.interactor.UseCase
import com.kardia.membership.domain.entities.passcode.VerifyPasscodeEntity
import com.kardia.membership.domain.repositories.PasscodeRepository
import javax.inject.Inject

class PostVerifyPasscodeUseCase
@Inject constructor(private val repository: PasscodeRepository) :
    UseCase<VerifyPasscodeEntity, PostVerifyPasscodeUseCase.Params>() {
    data class Params(val email: String?)

    override suspend fun run(params: Params): Either<Failure, VerifyPasscodeEntity> {
        return repository.verify(params)
    }
}