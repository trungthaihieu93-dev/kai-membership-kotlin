package com.kardia.membership.domain.api

import com.kardia.membership.domain.entities.passcode.*
import com.kardia.membership.domain.usecases.passcode.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

internal interface PasscodeAPI {
    companion object {
        private const val LOGIN = " v1/passcodes/login"
        private const val REGISTER = "v1/passcodes/register"
        private const val CHECK = "v1/passcodes/check"
        private const val FORGOT = "v1/passcodes/forgot"
        private const val RESET = "v1/passcodes/reset"
        private const val VERIFY = "v1/passcodes/verify"
    }

    @POST(LOGIN)
    fun login(@Body params: PostLoginPasscodeUseCase.Params): Call<LoginPasscodeEntity>

    @POST(REGISTER)
    fun register(@Body params: PostRegisterPasscodeUseCase.Params): Call<RegisterPasscodeEntity>

    @POST(CHECK)
    fun check(@Body params: PostCheckPasscodeUseCase.Params): Call<CheckPasscodeEntity>

    @POST(FORGOT)
    fun forgot(@Body params: PostForgotPasscodeUseCase.Params): Call<ForgotPasscodeEntity>

    @POST(RESET)
    fun reset(@Body params: PostResetPasscodeUseCase.Params): Call<ResetPasscodeEntity>

    @POST(VERIFY)
    fun verify(@Body params: PostVerifyPasscodeUseCase.Params): Call<VerifyPasscodeEntity>
}