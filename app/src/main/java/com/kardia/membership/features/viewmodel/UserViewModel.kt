package com.kardia.membership.features.viewmodel

import androidx.lifecycle.MutableLiveData
import com.kardia.membership.core.platform.BaseViewModel
import com.kardia.membership.data.entities.History
import com.kardia.membership.domain.entities.user.*
import com.kardia.membership.domain.usecases.user.*
import okhttp3.MultipartBody
import javax.inject.Inject

class UserViewModel
@Inject constructor(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val postUpdateInfoUseCase: PostUpdateInfoUseCase,
    private val postChangePasswordUseCase: PostChangePasswordUseCase,
    private val postUpdateAvatarUseCase: PostUpdateAvatarUseCase,
    private val getHistoryUseCase: GetHistoryUseCase

) : BaseViewModel() {
    var getUserInfoEntity: MutableLiveData<UserInfoEntity> = MutableLiveData()
    var updateUserInfoEntity: MutableLiveData<UpdateUserEntity> = MutableLiveData()
    var changePasswordEntity: MutableLiveData<ChangePasswordEntity> = MutableLiveData()
    var updateAvatarEntity: MutableLiveData<UpdateAvatarEntity> = MutableLiveData()
    var historyEntity: MutableLiveData<HistoryEntity> = MutableLiveData()

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

    fun getHistory() = getHistoryUseCase("") {
        it.fold(::handleFailure, ::handleHistory)
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

    private fun handleHistory(data: HistoryEntity) {
        historyEntity.value = data
    }
}