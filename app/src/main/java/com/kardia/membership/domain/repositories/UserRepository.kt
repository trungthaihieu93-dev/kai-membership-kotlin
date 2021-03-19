package com.kardia.membership.domain.repositories

import com.kardia.membership.core.exception.Failure
import com.kardia.membership.core.functional.Either
import com.kardia.membership.core.platform.NetworkHandler
import com.kardia.membership.domain.entities.user.*
import com.kardia.membership.domain.network.BaseNetwork
import com.kardia.membership.domain.services.UserService
import com.kardia.membership.domain.usecases.user.PostChangePasswordUseCase
import com.kardia.membership.domain.usecases.user.PostUpdateInfoUseCase
import okhttp3.MultipartBody
import javax.inject.Inject

interface UserRepository {
    fun getInfo(): Either<Failure, UserInfoEntity>
    fun updateInfo(params: PostUpdateInfoUseCase.Params): Either<Failure, UpdateUserEntity>
    fun changePassword(params: PostChangePasswordUseCase.Params): Either<Failure, ChangePasswordEntity>
    fun updateAvatar(file: MultipartBody.Part): Either<Failure, UpdateAvatarEntity>
    fun getHistory(): Either<Failure, HistoryEntity>
    class Network
    @Inject constructor(
        private val networkHandler: NetworkHandler,
        private val service: UserService
    ) : UserRepository, BaseNetwork() {
        override fun getInfo(): Either<Failure, UserInfoEntity> {
            return when (networkHandler.isConnected) {
                true -> request(service.getInfo(), {
                    it
                }, UserInfoEntity.empty())
                false, null -> Either.Left(Failure.NetworkConnection)
            }
        }

        override fun updateInfo(params: PostUpdateInfoUseCase.Params): Either<Failure, UpdateUserEntity> {
            return when (networkHandler.isConnected) {
                true -> request(service.updateInfo(params), {
                    it
                }, UpdateUserEntity.empty())
                false, null -> Either.Left(Failure.NetworkConnection)
            }
        }

        override fun changePassword(params: PostChangePasswordUseCase.Params): Either<Failure, ChangePasswordEntity> {
            return when (networkHandler.isConnected) {
                true -> request(service.changePassword(params), {
                    it
                }, ChangePasswordEntity.empty())
                false, null -> Either.Left(Failure.NetworkConnection)
            }
        }

        override fun updateAvatar(file: MultipartBody.Part): Either<Failure, UpdateAvatarEntity> {
            return when (networkHandler.isConnected) {
                true -> request(service.updateAvatar(file), {
                    it
                }, UpdateAvatarEntity.empty())
                false, null -> Either.Left(Failure.NetworkConnection)
            }
        }

        override fun getHistory(): Either<Failure, HistoryEntity> {
            return when (networkHandler.isConnected) {
                true -> request(service.getHistory(), {
                    it
                }, HistoryEntity.empty())
                false, null -> Either.Left(Failure.NetworkConnection)
            }
        }
    }
}