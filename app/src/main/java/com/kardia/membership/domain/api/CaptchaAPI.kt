package com.kardia.membership.domain.api

import com.kardia.membership.domain.entities.captcha.CreateCaptchaEntity
import com.kardia.membership.domain.entities.captcha.VerifyCaptchaEntity
import com.kardia.membership.domain.usecases.captcha.PostVerifyCaptchaUseCase
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

internal interface CaptchaAPI {
    companion object {
        private const val CAPTCHA_ID = "captchaId"
        private const val CREATE_CAPTCHA = "v1/captcha"
        private const val GET_CAPTCHA = "v1/captcha/{${CAPTCHA_ID}}"
        private const val VERIFY_CAPTCHA = "v1/captcha/verify"
    }

    @POST(CREATE_CAPTCHA)
    fun createCaptcha(): Call<CreateCaptchaEntity>

    @GET(GET_CAPTCHA)
    fun getCaptcha(@Path(CAPTCHA_ID) captchaId: String?): Call<ResponseBody>

    @POST(VERIFY_CAPTCHA)
    fun verifyCaptcha(@Body params: PostVerifyCaptchaUseCase.Params): Call<VerifyCaptchaEntity>
}