package com.kardia.membership.domain.usecases.auth

import com.kardia.membership.core.exception.Failure
import com.kardia.membership.core.functional.Either
import com.kardia.membership.core.interactor.UseCase
import com.kardia.membership.domain.entities.auth.ResetPasswordConfirmEntity
import com.kardia.membership.domain.repositories.AuthRepository
import javax.inject.Inject

class PostResetPasswordConfirmUseCase
@Inject constructor(private val repository: AuthRepository) :
    UseCase<ResetPasswordConfirmEntity, PostResetPasswordConfirmUseCase.Params>() {
    data class Params(val token: String, val password: String)

    override suspend fun run(params: Params): Either<Failure, ResetPasswordConfirmEntity> {
        return repository.resetPasswordConfirm(params)
    }
}