package com.kardia.membership.domain.api

import com.kardia.membership.domain.entities.tracking.TrackingActivityEntity
import com.kardia.membership.domain.usecases.tracking.PostTrackingActivityUseCase
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


internal interface TrackingAPI {
    companion object {
        private const val TRACKING_ACTIVITY = "v1/activity"
        private const val TRACKING_GAME = "v1/trackings"
    }

    @POST(TRACKING_ACTIVITY)
    fun trackingActivity(@Body params: PostTrackingActivityUseCase.Params): Call<TrackingActivityEntity>

}