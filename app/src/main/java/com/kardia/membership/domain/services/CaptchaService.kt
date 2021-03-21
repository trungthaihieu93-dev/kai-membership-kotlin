package com.kardia.membership.domain.services

import com.kardia.membership.domain.api.CaptchaAPI
import com.kardia.membership.domain.entities.captcha.CreateCaptchaEntity
import com.kardia.membership.domain.entities.captcha.VerifyCaptchaEntity
import com.kardia.membership.domain.usecases.captcha.PostVerifyCaptchaUseCase
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CaptchaService
@Inject constructor(retrofit: Retrofit) : CaptchaAPI {
    private val captchaAPI by lazy {
        retrofit.create(CaptchaAPI::class.java)
    }

    override fun createCaptcha(): Call<CreateCaptchaEntity> {
        return captchaAPI.createCaptcha()
    }

    override fun getCaptcha(captchaId: String?): Call<ResponseBody> {
        return captchaAPI.getCaptcha(captchaId)
    }

    override fun verifyCaptcha(params: PostVerifyCaptchaUseCase.Params): Call<VerifyCaptchaEntity> {
        return captchaAPI.verifyCaptcha(params)
    }

}