package com.kardia.membership.domain.repositories

import com.kardia.membership.core.exception.Failure
import com.kardia.membership.core.functional.Either
import com.kardia.membership.core.platform.NetworkHandler
import com.kardia.membership.domain.entities.captcha.CreateCaptchaEntity
import com.kardia.membership.domain.entities.captcha.VerifyCaptchaEntity
import com.kardia.membership.domain.network.BaseNetwork
import com.kardia.membership.domain.services.CaptchaService
import com.kardia.membership.domain.usecases.captcha.PostVerifyCaptchaUseCase
import okhttp3.ResponseBody
import javax.inject.Inject

interface CaptchaRepository {
    fun createCaptcha(): Either<Failure, CreateCaptchaEntity>
    fun getCaptcha(captchaId: String?): Either<Failure, ResponseBody>
    fun verifyCaptcha(params: PostVerifyCaptchaUseCase.Params): Either<Failure, VerifyCaptchaEntity>

    class Network
    @Inject constructor(
        private val networkHandler: NetworkHandler,
        private val service: CaptchaService
    ) : CaptchaRepository, BaseNetwork() {

        override fun createCaptcha(): Either<Failure, CreateCaptchaEntity> {
            return when (networkHandler.isConnected) {
                true -> request(service.createCaptcha(), {
                    it
                }, CreateCaptchaEntity.empty())
                false, null -> Either.Left(Failure.NetworkConnection)
            }
        }

        override fun getCaptcha(captchaId: String?): Either<Failure, ResponseBody> {
            return when (networkHandler.isConnected) {
                true -> request(service.getCaptcha(captchaId), {
                    it
                }, null)
                false, null -> Either.Left(Failure.NetworkConnection)
            }
        }

        override fun verifyCaptcha(params: PostVerifyCaptchaUseCase.Params): Either<Failure, VerifyCaptchaEntity> {
            return when (networkHandler.isConnected) {
                true -> request(service.verifyCaptcha(params), {
                    it
                }, null)
                false, null -> Either.Left(Failure.NetworkConnection)
            }
        }
    }
}