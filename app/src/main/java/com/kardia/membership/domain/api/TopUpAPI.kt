package com.kardia.membership.domain.api

import com.kardia.membership.domain.entities.config.ConfigEntity
import com.kardia.membership.domain.entities.topup.ClaimTopUpEntity
import com.kardia.membership.domain.usecases.topup.PostClaimTopUpUseCase
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

internal interface TopUpAPI {
    companion object {
        private const val CLAIM_TOP_UP = "v1/topup"
    }

    @POST(CLAIM_TOP_UP)
    fun claimTopUp(@Body params: PostClaimTopUpUseCase.Params): Call<ClaimTopUpEntity>

}