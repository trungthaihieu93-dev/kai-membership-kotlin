package com.kardia.membership.domain.services

import com.kardia.membership.domain.api.UserAPI
import com.kardia.membership.domain.entities.user.UserInfoEntity
import retrofit2.Call
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserService @Inject constructor(retrofit: Retrofit) : UserAPI {
    private val userAPI by lazy {
        retrofit.create(UserAPI::class.java)
    }

    override fun getInfo(): Call<UserInfoEntity> {
       return userAPI.getInfo()
    }
}