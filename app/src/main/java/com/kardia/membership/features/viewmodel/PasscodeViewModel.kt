package com.kardia.membership.features.viewmodel

import androidx.lifecycle.MutableLiveData
import com.kardia.membership.core.platform.BaseViewModel
import com.kardia.membership.domain.entities.auth.LoginAuthEntity
import com.kardia.membership.domain.entities.device.PasscodeDeviceEntity
import com.kardia.membership.domain.entities.passcode.*
import com.kardia.membership.domain.usecases.auth.PostLoginAuthUseCase
import com.kardia.membership.domain.usecases.passcode.*
import javax.inject.Inject

class PasscodeViewModel
@Inject constructor(
    private val postLoginPasscodeUseCase: PostLoginPasscodeUseCase,
    private val postCheckPasscodeUseCase: PostCheckPasscodeUseCase,
    private val postForgotPasscodeUseCase: PostForgotPasscodeUseCase,
    private val postRegisterPasscodeUseCase: PostRegisterPasscodeUseCase,
    private val postResetPasscodeUseCase: PostResetPasscodeUseCase,
    private val postVerifyPasscodeUseCase: PostVerifyPasscodeUseCase
) : BaseViewModel() {
    var loginEntity: MutableLiveData<LoginPasscodeEntity> = MutableLiveData()
    var checkEntity: MutableLiveData<CheckPasscodeEntity> = MutableLiveData()
    var forgotEntity: MutableLiveData<ForgotPasscodeEntity> = MutableLiveData()
    var registerEntity: MutableLiveData<RegisterPasscodeEntity> = MutableLiveData()
    var resetEntity: MutableLiveData<ResetPasscodeEntity> = MutableLiveData()
    var verifyEntity: MutableLiveData<VerifyPasscodeEntity> = MutableLiveData()

    fun loginPasscode(params: PostLoginPasscodeUseCase.Params) = postLoginPasscodeUseCase(params) {
        it.fold(::handleFailure, ::handleLoginPasscode)
    }

    private fun handleLoginPasscode(data: LoginPasscodeEntity) {
        this.loginEntity.value = data
    }

    fun checkPasscode(params: PostCheckPasscodeUseCase.Params) = postCheckPasscodeUseCase(params) {
        it.fold(::handleFailure, ::handleCheck)
    }

    private fun handleCheck(data: CheckPasscodeEntity) {
        this.checkEntity.value = data
    }

    fun forgotPasscode(params: PostForgotPasscodeUseCase.Params) =
        postForgotPasscodeUseCase(params) {
            it.fold(::handleFailure, ::handleForgot)
        }

    private fun handleForgot(data: ForgotPasscodeEntity) {
        this.forgotEntity.value = data
    }

    fun registerPasscode(params: PostRegisterPasscodeUseCase.Params) =
        postRegisterPasscodeUseCase(params) {
            it.fold(::handleFailure, ::handleRegisterPasscode)
        }

    private fun handleRegisterPasscode(data: RegisterPasscodeEntity) {
        this.registerEntity.value = data
    }

    fun resetPasscode(params: PostResetPasscodeUseCase.Params) = postResetPasscodeUseCase(params) {
        it.fold(::handleFailure, ::handleResetPasscode)
    }

    private fun handleResetPasscode(data: ResetPasscodeEntity) {
        this.resetEntity.value = data
    }

    fun verifyPasscode(params: PostVerifyPasscodeUseCase.Params) =
        postVerifyPasscodeUseCase(params) {
            it.fold(::handleFailure, ::handleVerifyPasscode)
        }

    private fun handleVerifyPasscode(data: VerifyPasscodeEntity) {
        this.verifyEntity.value = data
    }
}