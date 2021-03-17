package com.kardia.membership.domain.usecases.quest

import com.kardia.membership.core.exception.Failure
import com.kardia.membership.core.functional.Either
import com.kardia.membership.core.interactor.UseCase
import com.kardia.membership.domain.entities.quest.QuestsEntity
import com.kardia.membership.domain.repositories.QuestRepository
import javax.inject.Inject

class GetQuestsUseCase
@Inject constructor(private val repository: QuestRepository) :
    UseCase<QuestsEntity, String>() {

    override suspend fun run(params: String): Either<Failure, QuestsEntity> {
        return repository.getQuests()
    }
}