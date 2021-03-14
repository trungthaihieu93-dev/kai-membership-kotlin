package com.kardia.membership.domain.usecases.config

import com.kardia.membership.core.exception.Failure
import com.kardia.membership.core.functional.Either
import com.kardia.membership.core.interactor.UseCase
import com.kardia.membership.domain.entities.config.ConfigEntity
import com.kardia.membership.domain.repositories.ConfigRepository
import javax.inject.Inject

class GetConfigUseCase
@Inject constructor(private val repository: ConfigRepository) :
    UseCase<ConfigEntity, String>() {

    override suspend fun run(params: String): Either<Failure, ConfigEntity> {
        return repository.getConfig()
    }
}