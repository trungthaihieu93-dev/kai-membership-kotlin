package com.kardia.membership.domain.usecases.captcha

import com.kardia.membership.core.exception.Failure
import com.kardia.membership.core.functional.Either
import com.kardia.membership.core.interactor.UseCase
import com.kardia.membership.domain.repositories.CaptchaRepository
import okhttp3.ResponseBody
import javax.inject.Inject

class GetCaptchaUseCase
@Inject constructor(private val repository: CaptchaRepository) :
    UseCase<ResponseBody, String>() {
    override suspend fun run(params: String): Either<Failure, ResponseBody> {
        return repository.getCaptcha(params)
    }
}