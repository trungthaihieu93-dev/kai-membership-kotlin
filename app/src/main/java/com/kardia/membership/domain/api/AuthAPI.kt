package com.kardia.membership.domain.api

import com.kardia.membership.domain.entities.auth.LoginAuthEntity
import com.kardia.membership.domain.entities.auth.RegisterAuthEntity
import com.kardia.membership.domain.entities.auth.ResetPasswordConfirmEntity
import com.kardia.membership.domain.entities.auth.ResetPasswordEntity
import com.kardia.membership.domain.usecases.auth.PostLoginAuthUseCase
import com.kardia.membership.domain.usecases.auth.PostRegisterAuthUseCase
import com.kardia.membership.domain.usecases.auth.PostResetPasswordConfirmUseCase
import com.kardia.membership.domain.usecases.auth.PostResetPasswordUseCase
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

internal interface AuthAPI {
    companion object {
        private const val LOGIN_AUTH = "v1/auth/login"
        private const val REGISTER_AUTH = "v1/auth/register"
        private const val RESET_PASSWORD = "v1/auth/reset-password"
        private const val RESET_PASSWORD_CONFIRM = "v1/auth/reset-password-confirm"
    }

    @POST(LOGIN_AUTH)
    fun login(@Body params: PostLoginAuthUseCase.Params): Call<LoginAuthEntity>

    @POST(REGISTER_AUTH)
    fun register(@Body params: PostRegisterAuthUseCase.Params): Call<RegisterAuthEntity>

    @POST(RESET_PASSWORD)
    fun resetPassword(@Body params: PostResetPasswordUseCase.Params): Call<ResetPasswordEntity>

    @POST(RESET_PASSWORD_CONFIRM)
    fun resetPasswordConfirm(@Body params: PostResetPasswordConfirmUseCase.Params): Call<ResetPasswordConfirmEntity>
}