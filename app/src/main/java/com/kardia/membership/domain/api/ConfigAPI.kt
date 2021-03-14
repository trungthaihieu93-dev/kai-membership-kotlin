package com.kardia.membership.domain.api

import com.kardia.membership.domain.entities.config.ConfigEntity
import retrofit2.Call
import retrofit2.http.GET

internal interface ConfigAPI {
    companion object {
        private const val GET_CONFIG = "v1/configs"
    }

    @GET(GET_CONFIG)
    fun getConfig(): Call<ConfigEntity>

}