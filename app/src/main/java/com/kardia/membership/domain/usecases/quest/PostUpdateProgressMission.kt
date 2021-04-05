package com.kardia.membership.domain.usecases.quest

import com.kardia.membership.core.exception.Failure
import com.kardia.membership.core.functional.Either
import com.kardia.membership.core.interactor.UseCase
import com.kardia.membership.domain.entities.quest.UpdateProgressMissionEntity
import com.kardia.membership.domain.repositories.QuestRepository
import javax.inject.Inject

class PostUpdateProgressMission
@Inject constructor(private val repository: QuestRepository) :
    UseCase<UpdateProgressMissionEntity, PostUpdateProgressMission.Params>() {
    data class Params(val key: String?, val process: Int?, val description: String? = null)

    override suspend fun run(params: Params): Either<Failure, UpdateProgressMissionEntity> {
        return repository.updateProgressMission(params)
    }
}