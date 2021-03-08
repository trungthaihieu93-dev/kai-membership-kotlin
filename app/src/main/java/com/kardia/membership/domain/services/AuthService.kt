package com.kardia.membership.domain.services

import com.kardia.membership.domain.api.AuthAPI
import com.kardia.membership.domain.entities.auth.LoginAuthEntity
import com.kardia.membership.domain.entities.auth.RegisterAuthEntity
import com.kardia.membership.domain.entities.auth.ResetPasswordConfirmEntity
import com.kardia.membership.domain.entities.auth.ResetPasswordEntity
import com.kardia.membership.domain.usecases.auth.PostLoginAuthUseCase
import com.kardia.membership.domain.usecases.auth.PostRegisterAuthUseCase
import com.kardia.membership.domain.usecases.auth.PostResetPasswordConfirmUseCase
import com.kardia.membership.domain.usecases.auth.PostResetPasswordUseCase
import retrofit2.Call
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthService @Inject constructor(retrofit: Retrofit) : AuthAPI {
    private val authAPI by lazy {
        retrofit.create(AuthAPI::class.java)
    }

    override fun login(params: PostLoginAuthUseCase.Params): Call<LoginAuthEntity> {
        return authAPI.login(params)
    }

    override fun register(params: PostRegisterAuthUseCase.Params): Call<RegisterAuthEntity> {
        return authAPI.register(params)
    }

    override fun resetPassword(params: PostResetPasswordUseCase.Params): Call<ResetPasswordEntity> {
        return authAPI.resetPassword(params)
    }

    override fun resetPasswordConfirm(params: PostResetPasswordConfirmUseCase.Params): Call<ResetPasswordConfirmEntity> {
        return authAPI.resetPasswordConfirm(params)
    }

}