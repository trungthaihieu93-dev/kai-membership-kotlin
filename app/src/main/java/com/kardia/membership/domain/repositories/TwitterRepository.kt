package com.kardia.membership.domain.repositories

import com.kardia.membership.core.exception.Failure
import com.kardia.membership.core.functional.Either
import com.kardia.membership.core.platform.NetworkHandler
import com.kardia.membership.domain.entities.news.LatestNewsEntity
import com.kardia.membership.domain.network.BaseNetwork
import com.kardia.membership.domain.services.TwitterService
import javax.inject.Inject

interface TwitterRepository {
    fun getLatestNews(): Either<Failure, LatestNewsEntity>

    class Network
    @Inject constructor(
        private val networkHandler: NetworkHandler,
        private val service: TwitterService
    ) : TwitterRepository, BaseNetwork() {
        override fun getLatestNews(): Either<Failure, LatestNewsEntity> {
            return when (networkHandler.isConnected) {
                true -> request(service.getLatestNews(), {
                    it
                }, LatestNewsEntity.empty())
                false, null -> Either.Left(Failure.NetworkConnection)
            }
        }
    }
}