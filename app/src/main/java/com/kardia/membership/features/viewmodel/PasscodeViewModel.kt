package com.kardia.membership.features.viewmodel

import androidx.lifecycle.MutableLiveData
import com.kardia.membership.core.platform.BaseViewModel
import com.kardia.membership.domain.entities.auth.LoginAuthEntity
import com.kardia.membership.domain.entities.device.PasscodeDeviceEntity
import com.kardia.membership.domain.entities.passcode.CheckPasscodeEntity
import com.kardia.membership.domain.entities.passcode.ForgotPasscodeEntity
import com.kardia.membership.domain.entities.passcode.LoginPasscodeEntity
import com.kardia.membership.domain.usecases.auth.PostLoginAuthUseCase
import com.kardia.membership.domain.usecases.passcode.PostCheckPasscodeUseCase
import com.kardia.membership.domain.usecases.passcode.PostForgotPasscodeUseCase
import com.kardia.membership.domain.usecases.passcode.PostLoginPasscodeUseCase
import javax.inject.Inject

class PasscodeViewModel
@Inject constructor(
    private val postLoginPasscodeUseCase: PostLoginPasscodeUseCase,
    private val postCheckPasscodeUseCase: PostCheckPasscodeUseCase,
    private val postForgotPasscodeUseCase: PostForgotPasscodeUseCase
) : BaseViewModel() {
    var loginEntity: MutableLiveData<LoginPasscodeEntity> = MutableLiveData()
    var checkEntity: MutableLiveData<CheckPasscodeEntity> = MutableLiveData()
    var forgotEntity: MutableLiveData<ForgotPasscodeEntity> = MutableLiveData()

    fun loginPasscode(params: PostLoginPasscodeUseCase.Params) = postLoginPasscodeUseCase(params) {
        it.fold(::handleFailure, ::handleLogin)
    }

    private fun handleLogin(data: LoginPasscodeEntity) {
        this.loginEntity.value = data
    }

    fun checkPasscode(params: PostCheckPasscodeUseCase.Params) = postCheckPasscodeUseCase(params) {
        it.fold(::handleFailure, ::handleCheck)
    }

    private fun handleCheck(data: CheckPasscodeEntity) {
        this.checkEntity.value = data
    }

    fun forgotPasscode(params: PostForgotPasscodeUseCase.Params) = postForgotPasscodeUseCase(params) {
        it.fold(::handleFailure, ::handleForgot)
    }

    private fun handleForgot(data: ForgotPasscodeEntity) {
        this.forgotEntity.value = data
    }
}