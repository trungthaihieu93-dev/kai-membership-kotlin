package com.kardia.membership.domain.api

import com.kardia.membership.domain.entities.referral.ReferralEntity
import com.kardia.membership.domain.usecases.referral.PostReferralUseCase
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

internal interface ReferralAPI {
    companion object {
        private const val REFERRAL = "v1/referrals"
    }

    @POST(REFERRAL)
    fun referral(@Body params: PostReferralUseCase.Params): Call<ReferralEntity>
}