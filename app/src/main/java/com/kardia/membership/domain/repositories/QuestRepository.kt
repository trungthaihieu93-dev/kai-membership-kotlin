package com.kardia.membership.domain.repositories

import com.kardia.membership.core.exception.Failure
import com.kardia.membership.core.functional.Either
import com.kardia.membership.core.platform.NetworkHandler
import com.kardia.membership.domain.entities.quest.CheckProgressMissionEntity
import com.kardia.membership.domain.entities.quest.QuestsEntity
import com.kardia.membership.domain.entities.quest.UpdateProgressMissionEntity
import com.kardia.membership.domain.network.BaseNetwork
import com.kardia.membership.domain.services.QuestService
import com.kardia.membership.domain.usecases.quest.PostCheckProgressMission
import com.kardia.membership.domain.usecases.quest.PostUpdateProgressMission
import javax.inject.Inject

interface QuestRepository {
    fun getQuests(): Either<Failure, QuestsEntity>
    fun getQuestsUser(): Either<Failure, QuestsEntity>
    fun updateProgressMission(params: PostUpdateProgressMission.Params): Either<Failure, UpdateProgressMissionEntity>
    fun checkProgressMission(params: PostCheckProgressMission.Params): Either<Failure, CheckProgressMissionEntity>
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

        override fun updateProgressMission(params: PostUpdateProgressMission.Params): Either<Failure, UpdateProgressMissionEntity> {
            return when (networkHandler.isConnected) {
                true -> request(service.updateProgressMission(params), {
                    it
                }, UpdateProgressMissionEntity.empty())
                false, null -> Either.Left(Failure.NetworkConnection)
            }
        }

        override fun checkProgressMission(params: PostCheckProgressMission.Params): Either<Failure, CheckProgressMissionEntity> {
            return when (networkHandler.isConnected) {
                true -> request(service.checkProgressMission(params.user_id,params.key), {
                    it
                }, CheckProgressMissionEntity.empty())
                false, null -> Either.Left(Failure.NetworkConnection)
            }
        }
    }
}