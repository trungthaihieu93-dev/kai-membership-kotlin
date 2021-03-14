package com.kardia.membership.features.viewmodel

import androidx.lifecycle.MutableLiveData
import com.kardia.membership.core.platform.BaseViewModel
import com.kardia.membership.domain.entities.user.ChangePasswordEntity
import com.kardia.membership.domain.entities.user.UpdateAvatarEntity
import com.kardia.membership.domain.entities.user.UpdateUserEntity
import com.kardia.membership.domain.entities.user.UserInfoEntity
import com.kardia.membership.domain.usecases.user.GetUserInfoUseCase
import com.kardia.membership.domain.usecases.user.PostChangePasswordUseCase
import com.kardia.membership.domain.usecases.user.PostUpdateAvatarUseCase
import com.kardia.membership.domain.usecases.user.PostUpdateInfoUseCase
import okhttp3.MultipartBody
import javax.inject.Inject

class UserViewModel
@Inject constructor(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val postUpdateInfoUseCase: PostUpdateInfoUseCase,
    private val postChangePasswordUseCase: PostChangePasswordUseCase,
    private val postUpdateAvatarUseCase: PostUpdateAvatarUseCase
) : BaseViewModel() {
    var getUserInfoEntity: MutableLiveData<UserInfoEntity> = MutableLiveData()
    var updateUserInfoEntity: MutableLiveData<UpdateUserEntity> = MutableLiveData()
    var changePasswordEntity: MutableLiveData<ChangePasswordEntity> = MutableLiveData()
    var updateAvatarEntity: MutableLiveData<UpdateAvatarEntity> = MutableLiveData()

    fun getUserInfo() = getUserInfoUseCase("") {
        it.fold(::handleFailure, ::handleGetDataUserInfo)
    }

    fun updateUserInfo(params: PostUpdateInfoUseCase.Params) = postUpdateInfoUseCase(params) {
        it.fold(::handleFailure, ::handleUpdateDataUserInfo)
    }

    fun changePassword(params: PostChangePasswordUseCase.Params) =
        postChangePasswordUseCase(params) {
            it.fold(::handleFailure, ::handleChangePassword)
        }

    fun updateAvatar(params: MultipartBody.Part) = postUpdateAvatarUseCase(params) {
        it.fold(::handleFailure, ::handleUpdateAvatar)
    }


    private fun handleGetDataUserInfo(data: UserInfoEntity) {
        this.getUserInfoEntity.value = data
    }

    private fun handleUpdateDataUserInfo(data: UpdateUserEntity) {
        this.updateUserInfoEntity.value = data
    }

    private fun handleChangePassword(data: ChangePasswordEntity) {
        this.changePasswordEntity.value = data
    }

    private fun handleUpdateAvatar(data: UpdateAvatarEntity) {
        updateAvatarEntity.value = data
    }
}