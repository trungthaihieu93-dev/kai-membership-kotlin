package com.kardia.membership.domain.services

import com.kardia.membership.domain.api.TrackingAPI
import com.kardia.membership.domain.entities.tracking.TrackingActivityEntity
import com.kardia.membership.domain.usecases.tracking.PostTrackingActivityUseCase
import retrofit2.Call
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TrackingService
@Inject constructor(retrofit: Retrofit) : TrackingAPI {
    private val trackingAPI by lazy {
        retrofit.create(TrackingAPI::class.java)
    }

    override fun trackingActivity(params: PostTrackingActivityUseCase.Params): Call<TrackingActivityEntity> {
        return trackingAPI.trackingActivity(params)
    }
}