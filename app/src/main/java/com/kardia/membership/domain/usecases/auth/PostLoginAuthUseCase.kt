package com.kardia.membership.domain.usecases.auth

import com.kardia.membership.core.exception.Failure
import com.kardia.membership.core.functional.Either
import com.kardia.membership.core.interactor.UseCase
import com.kardia.membership.domain.entities.auth.LoginAuthEntity
import com.kardia.membership.domain.repositories.AuthRepository
import javax.inject.Inject

class PostLoginAuthUseCase
@Inject constructor(private val repository: AuthRepository) :
    UseCase<LoginAuthEntity, PostLoginAuthUseCase.Params>() {
    data class Params(val username: String, val password: String, val os: String)

    override suspend fun run(params: Params): Either<Failure, LoginAuthEntity> {
        return repository.login(params)
    }
}