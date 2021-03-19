package com.kardia.membership.domain.usecases.user

import com.kardia.membership.core.exception.Failure
import com.kardia.membership.core.functional.Either
import com.kardia.membership.core.interactor.UseCase
import com.kardia.membership.domain.entities.user.HistoryEntity
import com.kardia.membership.domain.repositories.UserRepository
import javax.inject.Inject

class GetHistoryUseCase @Inject constructor(private val repository: UserRepository) :
    UseCase<HistoryEntity, String>() {

    override suspend fun run(params: String): Either<Failure, HistoryEntity> {
        return repository.getHistory()
    }
}