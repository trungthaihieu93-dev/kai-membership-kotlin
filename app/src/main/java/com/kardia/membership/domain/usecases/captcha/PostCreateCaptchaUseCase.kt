package com.kardia.membership.domain.usecases.captcha

import com.kardia.membership.core.exception.Failure
import com.kardia.membership.core.functional.Either
import com.kardia.membership.core.interactor.UseCase
import com.kardia.membership.domain.entities.captcha.CreateCaptchaEntity
import com.kardia.membership.domain.repositories.CaptchaRepository
import javax.inject.Inject

class PostCreateCaptchaUseCase
@Inject constructor(private val repository: CaptchaRepository) :
    UseCase<CreateCaptchaEntity, String>() {
    override suspend fun run(params: String): Either<Failure, CreateCaptchaEntity> {
        return repository.createCaptcha()
    }
}