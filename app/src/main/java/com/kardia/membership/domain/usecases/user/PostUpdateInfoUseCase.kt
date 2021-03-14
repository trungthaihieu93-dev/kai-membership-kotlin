package com.kardia.membership.domain.usecases.user

import com.kardia.membership.core.exception.Failure
import com.kardia.membership.core.functional.Either
import com.kardia.membership.core.interactor.UseCase
import com.kardia.membership.domain.entities.user.UpdateUserEntity
import com.kardia.membership.domain.repositories.UserRepository
import javax.inject.Inject

class PostUpdateInfoUseCase
@Inject constructor(private val repository: UserRepository) :
    UseCase<UpdateUserEntity, PostUpdateInfoUseCase.Params>() {
    data class Params(
        val firstName: String?,
        val phone: String?,
        val birthday_time: Long? = null,
        val lastName: String? = " "
    )

    override suspend fun run(params: Params): Either<Failure, UpdateUserEntity> {
        return repository.updateInfo(params)
    }
}