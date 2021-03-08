package com.kardia.membership.domain.api

import com.kardia.membership.domain.entities.user.UserInfoEntity
import retrofit2.Call
import retrofit2.http.GET

internal interface UserAPI {
    companion object {
        private const val INFO = "v1/users/info"
    }

    @GET(INFO)
    fun getInfo(): Call<UserInfoEntity>

}