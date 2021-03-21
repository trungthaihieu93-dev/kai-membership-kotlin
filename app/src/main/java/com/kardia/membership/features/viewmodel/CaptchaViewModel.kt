package com.kardia.membership.features.viewmodel

import androidx.lifecycle.MutableLiveData
import com.kardia.membership.core.platform.BaseViewModel
import com.kardia.membership.domain.entities.captcha.CreateCaptchaEntity
import com.kardia.membership.domain.entities.captcha.VerifyCaptchaEntity
import com.kardia.membership.domain.usecases.captcha.GetCaptchaUseCase
import com.kardia.membership.domain.usecases.captcha.PostCreateCaptchaUseCase
import com.kardia.membership.domain.usecases.captcha.PostVerifyCaptchaUseCase
import okhttp3.ResponseBody
import javax.inject.Inject

class CaptchaViewModel
@Inject constructor(
    private val postCreateCaptchaUseCase: PostCreateCaptchaUseCase,
    private val getCaptchaUseCase: GetCaptchaUseCase,
    private val postVerifyCaptchaUseCase: PostVerifyCaptchaUseCase
) : BaseViewModel() {
    var createCaptchaEntity: MutableLiveData<CreateCaptchaEntity> = MutableLiveData()
    var getCaptchaEntity: MutableLiveData<ResponseBody> = MutableLiveData()
    var verifyCaptchaEntity: MutableLiveData<VerifyCaptchaEntity> = MutableLiveData()

    fun createCaptcha() =
        postCreateCaptchaUseCase("") {
            it.fold(::handleFailure, ::handleCreateCaptcha)
        }

    private fun handleCreateCaptcha(data: CreateCaptchaEntity) {
        this.createCaptchaEntity.value = data
    }

    fun getCaptcha(captchaId: String) =
        getCaptchaUseCase(captchaId) {
            it.fold(::handleFailure, ::handleGetCaptcha)
        }

    private fun handleGetCaptcha(data: ResponseBody) {
        this.getCaptchaEntity.value = data
    }

    fun verifyCaptcha(params: PostVerifyCaptchaUseCase.Params) =
        postVerifyCaptchaUseCase(params) {
            it.fold(::handleFailure, ::handleVerifyCaptcha)
        }

    private fun handleVerifyCaptcha(data: VerifyCaptchaEntity) {
        this.verifyCaptchaEntity.value = data
    }
}