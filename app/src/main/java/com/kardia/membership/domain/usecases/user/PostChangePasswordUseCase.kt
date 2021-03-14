package com.kardia.membership.domain.usecases.user

import com.kardia.membership.core.exception.Failure
import com.kardia.membership.core.functional.Either
import com.kardia.membership.core.interactor.UseCase
import com.kardia.membership.domain.entities.user.ChangePasswordEntity
import com.kardia.membership.domain.repositories.UserRepository
import javax.inject.Inject

class PostChangePasswordUseCase
@Inject constructor(private val repository: UserRepository) :
    UseCase<ChangePasswordEntity, PostChangePasswordUseCase.Params>() {
    data class Params(
        val password: String?,
        val new_password: String?
    )

    override suspend fun run(params: Params): Either<Failure, ChangePasswordEntity> {
        return repository.changePassword(params)
    }
}