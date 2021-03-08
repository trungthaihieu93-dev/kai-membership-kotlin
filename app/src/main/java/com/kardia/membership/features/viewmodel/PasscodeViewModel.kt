package com.kardia.membership.features.viewmodel

import androidx.lifecycle.MutableLiveData
import com.kardia.membership.core.platform.BaseViewModel
import com.kardia.membership.domain.entities.auth.LoginAuthEntity
import com.kardia.membership.domain.entities.device.PasscodeDeviceEntity
import com.kardia.membership.domain.entities.passcode.LoginPasscodeEntity
import com.kardia.membership.domain.usecases.auth.PostLoginAuthUseCase
import com.kardia.membership.domain.usecases.passcode.PostLoginPasscodeUseCase
import javax.inject.Inject

class PasscodeViewModel
@Inject constructor(
    private val postLoginPasscodeUseCase: PostLoginPasscodeUseCase
) : BaseViewModel() {
    var loginEntity: MutableLiveData<LoginPasscodeEntity> = MutableLiveData()

    fun loginPasscode(params: PostLoginPasscodeUseCase.Params) = postLoginPasscodeUseCase(params) {
        it.fold(::handleFailure, ::handleLogin)
    }

    private fun handleLogin(data: LoginPasscodeEntity) {
        this.loginEntity.value = data
    }
}