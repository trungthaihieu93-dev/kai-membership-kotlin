package com.kardia.membership.domain.usecases.quest

import com.kardia.membership.core.exception.Failure
import com.kardia.membership.core.functional.Either
import com.kardia.membership.core.interactor.UseCase
import com.kardia.membership.domain.entities.quest.CheckProgressMissionEntity
import com.kardia.membership.domain.repositories.QuestRepository
import javax.inject.Inject

class PostCheckProgressMission
@Inject constructor(private val repository: QuestRepository) :
    UseCase<CheckProgressMissionEntity, PostCheckProgressMission.Params>() {
    data class Params(val user_id: String?, val key: String? = null)

    override suspend fun run(params: Params): Either<Failure, CheckProgressMissionEntity> {
        return repository.checkProgressMission(params)
    }
}