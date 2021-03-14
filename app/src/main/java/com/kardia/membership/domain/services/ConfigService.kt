package com.kardia.membership.domain.services

import com.kardia.membership.domain.api.ConfigAPI
import com.kardia.membership.domain.entities.config.ConfigEntity
import retrofit2.Call
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConfigService
@Inject constructor(retrofit: Retrofit) : ConfigAPI {
    private val configAPI by lazy {
        retrofit.create(ConfigAPI::class.java)
    }

    override fun getConfig(): Call<ConfigEntity> {
        return configAPI.getConfig()
    }
}