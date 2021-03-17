package com.kardia.membership.features.viewmodel

import androidx.lifecycle.MutableLiveData
import com.kardia.membership.core.platform.BaseViewModel
import com.kardia.membership.domain.entities.auth.LoginAuthEntity
import com.kardia.membership.domain.entities.auth.ResetPasswordConfirmEntity
import com.kardia.membership.domain.entities.auth.ResetPasswordEntity
import com.kardia.membership.domain.usecases.auth.PostLoginAuthUseCase
import com.kardia.membership.domain.usecases.auth.PostResetPasswordConfirmUseCase
import com.kardia.membership.domain.usecases.auth.PostResetPasswordUseCase
import javax.inject.Inject

class AuthViewModel
@Inject constructor(
    private val postLoginAuthUseCase: PostLoginAuthUseCase,
    private val postResetPasswordUseCase: PostResetPasswordUseCase,
    private val postResetPasswordConfirmUseCase: PostResetPasswordConfirmUseCase
) : BaseViewModel() {
    var loginEntity: MutableLiveData<LoginAuthEntity> = MutableLiveData()
    var resetPasswordEntity: MutableLiveData<ResetPasswordEntity> = MutableLiveData()
    var resetPasswordConfirmEntity: MutableLiveData<ResetPasswordConfirmEntity> = MutableLiveData()

    fun loginAuth(params:PostLoginAuthUseCase.Params) = postLoginAuthUseCase(params) {
        it.fold(::handleFailure, ::handleLogin)
    }

    private fun handleLogin(data: LoginAuthEntity) {
        this.loginEntity.value = data
    }

    fun resetPassword(params:PostResetPasswordUseCase.Params) = postResetPasswordUseCase(params) {
        it.fold(::handleFailure, ::handleResetPassword)
    }

    private fun handleResetPassword(data: ResetPasswordEntity) {
        this.resetPasswordEntity.value = data
    }

    fun resetPasswordConfirm(params:PostResetPasswordConfirmUseCase.Params) = postResetPasswordConfirmUseCase(params) {
        it.fold(::handleFailure, ::handleResetPasswordConfirm)
    }

    private fun handleResetPasswordConfirm(data: ResetPasswordConfirmEntity) {
        this.resetPasswordConfirmEntity.value = data
    }
}