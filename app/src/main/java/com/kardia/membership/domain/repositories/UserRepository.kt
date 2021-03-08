package com.kardia.membership.domain.repositories

import com.kardia.membership.core.exception.Failure
import com.kardia.membership.core.functional.Either
import com.kardia.membership.core.platform.NetworkHandler
import com.kardia.membership.domain.entities.user.UserInfoEntity
import com.kardia.membership.domain.network.BaseNetwork
import com.kardia.membership.domain.services.UserService
import javax.inject.Inject

interface UserRepository {
    fun getInfo(): Either<Failure, UserInfoEntity>

    class Network
    @Inject constructor(
        private val networkHandler: NetworkHandler,
        private val service: UserService
    ) : UserRepository, BaseNetwork() {
        override fun getInfo(): Either<Failure, UserInfoEntity> {
            return when (networkHandler.isConnected) {
                true -> request(service.getInfo(), {
                    it
                }, UserInfoEntity.empty())
                false, null -> Either.Left(Failure.NetworkConnection)
            }
        }
    }
}