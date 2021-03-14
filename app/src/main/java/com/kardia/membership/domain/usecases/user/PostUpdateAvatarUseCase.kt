package com.kardia.membership.domain.usecases.user

import com.kardia.membership.core.interactor.UseCase
import com.kardia.membership.domain.entities.BaseEntities
import com.kardia.membership.domain.entities.user.UpdateAvatarEntity
import com.kardia.membership.domain.repositories.UserRepository
import okhttp3.MultipartBody
import javax.inject.Inject

class PostUpdateAvatarUseCase
@Inject constructor(private val repository: UserRepository) :
    UseCase<UpdateAvatarEntity, MultipartBody.Part>() {

    override suspend fun run(params: MultipartBody.Part) =
        repository.updateAvatar(params)
}