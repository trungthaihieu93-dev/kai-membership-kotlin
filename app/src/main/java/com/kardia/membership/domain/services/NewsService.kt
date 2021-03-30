package com.kardia.membership.domain.services

import com.kardia.membership.domain.api.NewsAPI
import com.kardia.membership.domain.entities.news.FeatureNewsEntity
import retrofit2.Call
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsService
@Inject constructor(retrofit: Retrofit) : NewsAPI {
    private val newsAPI by lazy {
        retrofit.newBuilder().baseUrl("https://api.rss2json.com/").build()
            .create(NewsAPI::class.java)
    }

    override fun getFeatureNews(): Call<FeatureNewsEntity> {
        return newsAPI.getFeatureNews()
    }

}