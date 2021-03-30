package com.kardia.membership.domain.services

import com.kardia.membership.domain.api.TwitterAPI
import com.kardia.membership.domain.entities.news.LatestNewsEntity
import retrofit2.Call
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TwitterService
@Inject constructor(retrofit: Retrofit) : TwitterAPI {
    private val twitterAPI by lazy {
        retrofit.newBuilder().baseUrl("https://api.twitter.com/").build()
            .create(TwitterAPI::class.java)
    }

    override fun getLatestNews(): Call<LatestNewsEntity> {
        return twitterAPI.getLatestNews()
    }

}