package com.kardia.membership.domain.services

import com.kardia.membership.domain.api.QuestAPI
import com.kardia.membership.domain.entities.quest.QuestsEntity
import com.kardia.membership.domain.entities.quest.UpdateProgressMissionEntity
import com.kardia.membership.domain.usecases.quest.PostUpdateProgressMission
import retrofit2.Call
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuestService
@Inject constructor(retrofit: Retrofit) : QuestAPI {
    private val questAPI by lazy {
        retrofit.create(QuestAPI::class.java)
    }

    override fun getQuests(): Call<QuestsEntity> {
        return questAPI.getQuests()
    }

    override fun getQuestsUsers(): Call<QuestsEntity> {
        return questAPI.getQuestsUsers()
    }

    override fun updateProgressMission(params: PostUpdateProgressMission.Params): Call<UpdateProgressMissionEntity> {
        return questAPI.updateProgressMission(params)
    }
}