package com.kardia.membership.features.viewmodel

import androidx.lifecycle.MutableLiveData
import com.kardia.membership.core.platform.BaseViewModel
import com.kardia.membership.domain.entities.quest.QuestsEntity
import com.kardia.membership.domain.entities.quest.UpdateProgressMissionEntity
import com.kardia.membership.domain.usecases.quest.GetQuestsUserUseCase
import com.kardia.membership.domain.usecases.quest.GetQuestsUseCase
import com.kardia.membership.domain.usecases.quest.PostUpdateProgressMission
import javax.inject.Inject

class QuestViewModel
@Inject constructor(
    private val getQuestsUseCase: GetQuestsUseCase,
    private val getQuestsUserUseCase: GetQuestsUserUseCase,
    private val postUpdateProgressMission: PostUpdateProgressMission

) : BaseViewModel() {
    var questsEntity: MutableLiveData<QuestsEntity> = MutableLiveData()
    var questsUserEntity: MutableLiveData<QuestsEntity> = MutableLiveData()
    var updateProgressMissionEntity: MutableLiveData<UpdateProgressMissionEntity> =
        MutableLiveData()

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

    fun updateProgressMission(params: PostUpdateProgressMission.Params) =
        postUpdateProgressMission(params) {
            it.fold(::handleFailure, ::handleUpdateProgressMissionEntity)
        }

    private fun handleUpdateProgressMissionEntity(data: UpdateProgressMissionEntity) {
        this.updateProgressMissionEntity.value = data
    }
}