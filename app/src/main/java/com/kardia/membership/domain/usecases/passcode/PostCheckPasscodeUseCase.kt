package com.kardia.membership.domain.usecases.passcode

import com.kardia.membership.core.exception.Failure
import com.kardia.membership.core.functional.Either
import com.kardia.membership.core.interactor.UseCase
import com.kardia.membership.domain.entities.passcode.CheckPasscodeEntity
import com.kardia.membership.domain.repositories.PasscodeRepository
import javax.inject.Inject

class PostCheckPasscodeUseCase
@Inject constructor(private val repository: PasscodeRepository) :
    UseCase<CheckPasscodeEntity, PostCheckPasscodeUseCase.Params>() {
    data class Params(
        val passcode: String,
        val mobile_unique_id: String?
    )

    override suspend fun run(params: Params): Either<Failure, CheckPasscodeEntity> {
        return repository.check(params)
    }
}