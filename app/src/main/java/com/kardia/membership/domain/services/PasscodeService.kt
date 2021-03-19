package com.kardia.membership.domain.services

import com.kardia.membership.domain.api.PasscodeAPI
import com.kardia.membership.domain.entities.auth.LoginAuthEntity
import com.kardia.membership.domain.entities.passcode.CheckPasscodeEntity
import com.kardia.membership.domain.entities.passcode.ForgotPasscodeEntity
import com.kardia.membership.domain.entities.passcode.LoginPasscodeEntity
import com.kardia.membership.domain.entities.passcode.RegisterPasscodeEntity
import com.kardia.membership.domain.usecases.passcode.PostCheckPasscodeUseCase
import com.kardia.membership.domain.usecases.passcode.PostForgotPasscodeUseCase
import com.kardia.membership.domain.usecases.passcode.PostLoginPasscodeUseCase
import com.kardia.membership.domain.usecases.passcode.PostRegisterPasscodeUseCase
import retrofit2.Call
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PasscodeService @Inject constructor(retrofit: Retrofit) : PasscodeAPI {
    private val passcodeAPI by lazy {
        retrofit.create(PasscodeAPI::class.java)
    }

    override fun login(params: PostLoginPasscodeUseCase.Params): Call<LoginPasscodeEntity> {
        return passcodeAPI.login(params)
    }

    override fun register(params: PostRegisterPasscodeUseCase.Params): Call<RegisterPasscodeEntity> {
        return passcodeAPI.register(params)
    }

    override fun check(params: PostCheckPasscodeUseCase.Params): Call<CheckPasscodeEntity> {
        return passcodeAPI.check(params)
    }

    override fun forgot(params: PostForgotPasscodeUseCase.Params): Call<ForgotPasscodeEntity> {
        return passcodeAPI.forgot(params)
    }

}