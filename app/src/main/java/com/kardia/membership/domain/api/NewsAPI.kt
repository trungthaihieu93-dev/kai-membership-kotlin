package com.kardia.membership.domain.api

import com.kardia.membership.domain.entities.news.FeatureNewsEntity
import retrofit2.Call
import retrofit2.http.GET

internal interface NewsAPI {
    companion object {
        private const val GET_FEATURE_NEWS = "v1/api.json?rss_url=https://medium.com/feed/kardiachain"
    }

    @GET(GET_FEATURE_NEWS)
    fun getFeatureNews(): Call<FeatureNewsEntity>

}