package com.kardia.membership.domain.api

import com.kardia.membership.domain.entities.user.ChangePasswordEntity
import com.kardia.membership.domain.entities.user.UpdateAvatarEntity
import com.kardia.membership.domain.entities.user.UpdateUserEntity
import com.kardia.membership.domain.entities.user.UserInfoEntity
import com.kardia.membership.domain.usecases.user.PostChangePasswordUseCase
import com.kardia.membership.domain.usecases.user.PostUpdateInfoUseCase
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

internal interface UserAPI {
    companion object {
        private const val INFO = "v1/users/info"
        private const val CHANGE_PASSWORD = "v1/users/change-password"
        private const val UPDATE_AVATAR = "v1/users/avatar"
    }

    @GET(INFO)
    fun getInfo(): Call<UserInfoEntity>

    @POST(INFO)
    fun updateInfo(@Body params:PostUpdateInfoUseCase.Params): Call<UpdateUserEntity>

    @POST(CHANGE_PASSWORD)
    fun changePassword(@Body params:PostChangePasswordUseCase.Params): Call<ChangePasswordEntity>

    @Multipart
    @POST(UPDATE_AVATAR)
    fun updateAvatar(@Part file: MultipartBody.Part): Call<UpdateAvatarEntity>
}