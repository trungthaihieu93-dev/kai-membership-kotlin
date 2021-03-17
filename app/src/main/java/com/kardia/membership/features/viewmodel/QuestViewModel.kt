package com.kardia.membership.features.viewmodel

import androidx.lifecycle.MutableLiveData
import com.kardia.membership.core.platform.BaseViewModel
import com.kardia.membership.domain.entities.quest.QuestsEntity
import com.kardia.membership.domain.usecases.quest.GetQuestsUserUseCase
import com.kardia.membership.domain.usecases.quest.GetQuestsUseCase
import javax.inject.Inject

class QuestViewModel
@Inject constructor(
    private val getQuestsUseCase: GetQuestsUseCase,
    private val getQuestsUserUseCase: GetQuestsUserUseCase
) : BaseViewModel() {
    var questsEntity: MutableLiveData<QuestsEntity> = MutableLiveData()
    var questsUserEntity: MutableLiveData<QuestsEntity> = MutableLiveData()

    fun getQuests() = getQuestsUseCase("") {
        it.fold(::handleFailure, ::handleQuestsEntity)
    }

    private fun handleQuestsEntity(data: QuestsEntity) {
        this.questsEntity.value = data
    }

    fun getQuestsUser() = getQuestsUserUseCase("") {
        it.fold(::handleFailure, ::handleQuestsUserEntity)
    }

    private fun handleQuestsUserEntity(data: QuestsEntity) {
        this.questsUserEntity.value = data
    }
}