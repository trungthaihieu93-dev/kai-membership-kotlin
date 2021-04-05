package com.kardia.membership.domain.services

import com.kardia.membership.domain.api.ReferralAPI
import com.kardia.membership.domain.entities.referral.ReferralEntity
import com.kardia.membership.domain.usecases.referral.PostReferralUseCase
import retrofit2.Call
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReferralService
@Inject constructor(retrofit: Retrofit) : ReferralAPI {
    private val referralAPI by lazy {
        retrofit.create(ReferralAPI::class.java)
    }

    override fun referral(params: PostReferralUseCase.Params): Call<ReferralEntity> {
        return referralAPI.referral(params)
    }
}