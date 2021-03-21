package com.kardia.membership.domain.usecases.captcha

import com.kardia.membership.core.exception.Failure
import com.kardia.membership.core.functional.Either
import com.kardia.membership.core.interactor.UseCase
import com.kardia.membership.domain.entities.captcha.VerifyCaptchaEntity
import com.kardia.membership.domain.repositories.CaptchaRepository
import javax.inject.Inject

class PostVerifyCaptchaUseCase
@Inject constructor(private val repository: CaptchaRepository) :
    UseCase<VerifyCaptchaEntity, PostVerifyCaptchaUseCase.Params>() {
    data class Params(val captcha_id: String, val captcha: String)

    override suspend fun run(params: Params): Either<Failure, VerifyCaptchaEntity> {
        return repository.verifyCaptcha(params)
    }
}