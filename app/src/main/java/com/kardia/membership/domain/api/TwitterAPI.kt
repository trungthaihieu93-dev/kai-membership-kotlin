package com.kardia.membership.domain.api

import com.kardia.membership.domain.entities.news.LatestNewsEntity
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

internal interface TwitterAPI {
    companion object {
        private const val GET_LATEST_NEW = "1.1/search/tweets.json?q=from:kardiachain%20AND%20-filter:retweets%20AND%20-filter:replies&count=20&result_type=recent&tweet_mode=extended"
    }


    @GET(GET_LATEST_NEW)
    @Headers("No-Authentication: true",
        "Authorization: Bearer AAAAAAAAAAAAAAAAAAAAAMHMNwEAAAAATHxIoEgKUYhdcjDBnVGJIpgPL54%3Dane0BGzcZ6gJjEYChdCayhs4wKN1KEgwZRGPM44HOxMOz32Kli")
    fun getLatestNews(): Call<LatestNewsEntity>

}