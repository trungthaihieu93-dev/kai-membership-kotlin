package com.kardia.membership.domain.usecases.news

import com.kardia.membership.core.exception.Failure
import com.kardia.membership.core.functional.Either
import com.kardia.membership.core.interactor.UseCase
import com.kardia.membership.domain.entities.news.FeatureNewsEntity
import com.kardia.membership.domain.repositories.NewsRepository
import javax.inject.Inject

class GetFeatureNewsUseCase @Inject constructor(private val repository: NewsRepository) :
    UseCase<FeatureNewsEntity, String>() {

    override suspend fun run(params: String): Either<Failure, FeatureNewsEntity> {
        return repository.getFeatureNews()
    }
}