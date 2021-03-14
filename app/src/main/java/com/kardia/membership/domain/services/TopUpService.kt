package com.kardia.membership.domain.services

import com.kardia.membership.domain.api.TopUpAPI
import com.kardia.membership.domain.entities.config.ConfigEntity
import com.kardia.membership.domain.entities.topup.ClaimTopUpEntity
import com.kardia.membership.domain.usecases.topup.PostClaimTopUpUseCase
import retrofit2.Call
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TopUpService
@Inject constructor(retrofit: Retrofit) : TopUpAPI {
    private val topUpAPI by lazy {
        retrofit.create(TopUpAPI::class.java)
    }

    override fun claimTopUp(params: PostClaimTopUpUseCase.Params): Call<ClaimTopUpEntity> {
        return topUpAPI.claimTopUp(params)
    }
}