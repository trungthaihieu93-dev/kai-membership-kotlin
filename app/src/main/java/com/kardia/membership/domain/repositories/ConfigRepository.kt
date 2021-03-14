package com.kardia.membership.domain.repositories

import com.kardia.membership.core.exception.Failure
import com.kardia.membership.core.functional.Either
import com.kardia.membership.core.platform.NetworkHandler
import com.kardia.membership.domain.entities.config.ConfigEntity
import com.kardia.membership.domain.network.BaseNetwork
import com.kardia.membership.domain.services.ConfigService
import javax.inject.Inject

interface ConfigRepository {
    fun getConfig(): Either<Failure, ConfigEntity>

    class Network
    @Inject constructor(
        private val networkHandler: NetworkHandler,
        private val service: ConfigService
    ) : ConfigRepository, BaseNetwork() {
        override fun getConfig(): Either<Failure, ConfigEntity> {
            return when (networkHandler.isConnected) {
                true -> request(service.getConfig(), {
                    it
                }, ConfigEntity.empty())
                false, null -> Either.Left(Failure.NetworkConnection)
            }
        }

    }
}