package com.kardia.membership.features.viewmodel

import androidx.lifecycle.MutableLiveData
import com.kardia.membership.core.platform.BaseViewModel
import com.kardia.membership.domain.entities.user.UserInfoEntity
import com.kardia.membership.domain.usecases.user.GetUserInfoUseCase
import javax.inject.Inject

class UserViewModel
@Inject constructor(
    private val getUserInfoUseCase: GetUserInfoUseCase
) : BaseViewModel() {
    var userInfoEntity: MutableLiveData<UserInfoEntity> = MutableLiveData()

    fun getUserInfo() = getUserInfoUseCase("") {
        it.fold(::handleFailure, ::handleDataUserInfo)
    }

    private fun handleDataUserInfo(data: UserInfoEntity) {
        this.userInfoEntity.value = data
    }
}