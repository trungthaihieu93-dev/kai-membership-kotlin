package com.kardia.membership.domain.usecases.auth

import com.kardia.membership.core.exception.Failure
import com.kardia.membership.core.functional.Either
import com.kardia.membership.core.interactor.UseCase
import com.kardia.membership.domain.entities.auth.RegisterAuthEntity
import com.kardia.membership.domain.repositories.AuthRepository
import javax.inject.Inject

class PostRegisterAuthUseCase
@Inject constructor(private val repository: AuthRepository) :
    UseCase<RegisterAuthEntity, PostRegisterAuthUseCase.Params>() {
    data class Params(val username: String, val password: String, val email: String, val first_name: String, val last_name: String)

    override suspend fun run(params: Params): Either<Failure, RegisterAuthEntity> {
        return repository.register(params)
    }
}