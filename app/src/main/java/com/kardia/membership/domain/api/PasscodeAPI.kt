package com.kardia.membership.domain.api

import com.kardia.membership.domain.entities.passcode.LoginPasscodeEntity
import com.kardia.membership.domain.entities.passcode.RegisterPasscodeEntity
import com.kardia.membership.domain.usecases.passcode.PostLoginPasscodeUseCase
import com.kardia.membership.domain.usecases.passcode.PostRegisterPasscodeUseCase
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

internal interface PasscodeAPI {
    companion object {
        private const val LOGIN = " v1/passcodes/login"
        private const val REGISTER = "v1/passcodes/register"

    }

    @POST(LOGIN)
    fun login(@Body params: PostLoginPasscodeUseCase.Params): Call<LoginPasscodeEntity>

    @POST(REGISTER)
    fun register(@Body params: PostRegisterPasscodeUseCase.Params): Call<RegisterPasscodeEntity>

}