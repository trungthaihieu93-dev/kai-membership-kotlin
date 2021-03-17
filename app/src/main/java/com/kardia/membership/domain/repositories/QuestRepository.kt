package com.kardia.membership.domain.repositories

import com.kardia.membership.core.exception.Failure
import com.kardia.membership.core.functional.Either
import com.kardia.membership.core.platform.NetworkHandler
import com.kardia.membership.domain.entities.quest.QuestsEntity
import com.kardia.membership.domain.network.BaseNetwork
import com.kardia.membership.domain.services.QuestService
import javax.inject.Inject

interface QuestRepository {
    fun getQuests(): Either<Failure, QuestsEntity>
    fun getQuestsUser(): Either<Failure, QuestsEntity>

    class Network
    @Inject constructor(
        private val networkHandler: NetworkHandler,
        private val service: QuestService
    ) : QuestRepository, BaseNetwork() {
        override fun getQuests(): Either<Failure, QuestsEntity> {
            return when (networkHandler.isConnected) {
                true -> request(service.getQuests(), {
                    it
                }, QuestsEntity.empty())
                false, null -> Either.Left(Failure.NetworkConnection)
            }
        }

        override fun getQuestsUser(): Either<Failure, QuestsEntity> {
            return when (networkHandler.isConnected) {
                true -> request(service.getQuestsUsers(), {
                    it
                }, QuestsEntity.empty())
                false, null -> Either.Left(Failure.NetworkConnection)
            }
        }
    }
}