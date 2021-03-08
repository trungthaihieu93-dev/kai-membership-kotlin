package com.kardia.membership.features.viewmodel

import androidx.lifecycle.MutableLiveData
import com.kardia.membership.core.platform.BaseViewModel
import com.kardia.membership.domain.entities.auth.LoginAuthEntity
import com.kardia.membership.domain.usecases.auth.PostLoginAuthUseCase
import javax.inject.Inject

class AuthViewModel
@Inject constructor(
    private val postLoginAuthUseCase: PostLoginAuthUseCase
) : BaseViewModel() {
    var loginEntity: MutableLiveData<LoginAuthEntity> = MutableLiveData()

    fun loginAuth(params:PostLoginAuthUseCase.Params) = postLoginAuthUseCase(params) {
        it.fold(::handleFailure, ::handleLogin)
    }

    private fun handleLogin(data: LoginAuthEntity) {
        this.loginEntity.value = data
    }
}