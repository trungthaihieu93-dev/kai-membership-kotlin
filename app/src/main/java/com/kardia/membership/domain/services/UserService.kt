package com.kardia.membership.domain.services

import com.kardia.membership.domain.api.UserAPI
import com.kardia.membership.domain.entities.user.*
import com.kardia.membership.domain.usecases.user.PostChangePasswordUseCase
import com.kardia.membership.domain.usecases.user.PostUpdateInfoUseCase
import okhttp3.MultipartBody
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

    override fun updateInfo(params: PostUpdateInfoUseCase.Params): Call<UpdateUserEntity> {
        return userAPI.updateInfo(params)
    }

    override fun changePassword(params: PostChangePasswordUseCase.Params): Call<ChangePasswordEntity> {
        return userAPI.changePassword(params)
    }

    override fun updateAvatar(file: MultipartBody.Part): Call<UpdateAvatarEntity> {
        return userAPI.updateAvatar(file)
    }

    override fun getHistory(): Call<HistoryEntity> {
        return userAPI.getHistory()
    }
}