package com.kardia.membership.domain.usecases.news

import com.kardia.membership.core.exception.Failure
import com.kardia.membership.core.functional.Either
import com.kardia.membership.core.interactor.UseCase
import com.kardia.membership.domain.entities.news.LatestNewsEntity
import com.kardia.membership.domain.repositories.TwitterRepository
import javax.inject.Inject

class GetLatestNewsUseCase @Inject constructor(private val repository: TwitterRepository) :
    UseCase<LatestNewsEntity, String>() {

    override suspend fun run(params: String): Either<Failure, LatestNewsEntity> {
        return repository.getLatestNews()
    }
}