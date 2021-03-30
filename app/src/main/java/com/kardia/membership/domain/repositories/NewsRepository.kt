package com.kardia.membership.domain.repositories

import com.kardia.membership.core.exception.Failure
import com.kardia.membership.core.functional.Either
import com.kardia.membership.core.platform.NetworkHandler
import com.kardia.membership.domain.entities.news.FeatureNewsEntity
import com.kardia.membership.domain.network.BaseNetwork
import com.kardia.membership.domain.services.NewsService
import javax.inject.Inject

interface NewsRepository {
    fun getFeatureNews(): Either<Failure, FeatureNewsEntity>

    class Network
    @Inject constructor(
        private val networkHandler: NetworkHandler,
        private val service: NewsService
    ) : NewsRepository, BaseNetwork() {
        override fun getFeatureNews(): Either<Failure, FeatureNewsEntity> {
            return when (networkHandler.isConnected) {
                true -> request(service.getFeatureNews(), {
                    it
                }, FeatureNewsEntity.empty())
                false, null -> Either.Left(Failure.NetworkConnection)
            }
        }
    }
}