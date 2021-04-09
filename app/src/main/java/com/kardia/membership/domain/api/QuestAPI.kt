package com.kardia.membership.domain.api

import com.kardia.membership.domain.entities.quest.CheckProgressMissionEntity
import com.kardia.membership.domain.entities.quest.QuestsEntity
import com.kardia.membership.domain.entities.quest.UpdateProgressMissionEntity
import com.kardia.membership.domain.usecases.quest.PostCheckProgressMission
import com.kardia.membership.domain.usecases.quest.PostUpdateProgressMission
import retrofit2.Call
import retrofit2.http.*

internal interface QuestAPI {
    companion object {
        private const val QUESTS = "v1/quests"
        private const val QUESTS_USER = "v1/quests/users"
        private const val CHECK_QUESTS = "v1/quests/get"
    }

    @GET(QUESTS)
    fun getQuests(): Call<QuestsEntity>

    @GET(QUESTS_USER)
    fun getQuestsUsers(): Call<QuestsEntity>

    @POST(QUESTS)
    fun updateProgressMission(@Body params: PostUpdateProgressMission.Params): Call<UpdateProgressMissionEntity>

    @GET(CHECK_QUESTS)
    fun checkProgressMission(@Query("user_id") user_id: String?,
                             @Query("key") key: String?): Call<CheckProgressMissionEntity>
}