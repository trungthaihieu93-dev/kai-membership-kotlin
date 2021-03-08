package com.kardia.membership.domain.usecases.auth

import com.kardia.membership.core.exception.Failure
import com.kardia.membership.core.functional.Either
import com.kardia.membership.core.interactor.UseCase
import com.kardia.membership.domain.entities.auth.ResetPasswordEntity
import com.kardia.membership.domain.repositories.AuthRepository
import javax.inject.Inject

class PostResetPasswordUseCase
@Inject constructor(private val repository: AuthRepository) :
    UseCase<ResetPasswordEntity, PostResetPasswordUseCase.Params>() {
    data class Params(val email: String)

    override suspend fun run(params: Params): Either<Failure, ResetPasswordEntity> {
        return repository.resetPassword(params)
    }
}