package com.kardia.membership.domain.repositories

import com.kardia.membership.core.exception.Failure
import com.kardia.membership.core.functional.Either
import com.kardia.membership.core.platform.NetworkHandler
import com.kardia.membership.domain.entities.auth.LoginAuthEntity
import com.kardia.membership.domain.entities.auth.RegisterAuthEntity
import com.kardia.membership.domain.entities.auth.ResetPasswordConfirmEntity
import com.kardia.membership.domain.entities.auth.ResetPasswordEntity
import com.kardia.membership.domain.network.BaseNetwork
import com.kardia.membership.domain.services.AuthService
import com.kardia.membership.domain.usecases.auth.PostLoginAuthUseCase
import com.kardia.membership.domain.usecases.auth.PostRegisterAuthUseCase
import com.kardia.membership.domain.usecases.auth.PostResetPasswordConfirmUseCase
import com.kardia.membership.domain.usecases.auth.PostResetPasswordUseCase
import javax.inject.Inject

interface AuthRepository {
    fun login(params:PostLoginAuthUseCase.Params): Either<Failure, LoginAuthEntity>
    fun register(params:PostRegisterAuthUseCase.Params): Either<Failure, RegisterAuthEntity>
    fun resetPassword(params:PostResetPasswordUseCase.Params): Either<Failure, ResetPasswordEntity>
    fun resetPasswordConfirm(params: PostResetPasswordConfirmUseCase.Params): Either<Failure, ResetPasswordConfirmEntity>
    class Network
    @Inject constructor(
        private val networkHandler: NetworkHandler,
        private val service: AuthService
    ) : AuthRepository, BaseNetwork() {
        override fun login(params: PostLoginAuthUseCase.Params): Either<Failure, LoginAuthEntity> {
            return when (networkHandler.isConnected) {
                true -> request(service.login(params), {
                    it
                }, LoginAuthEntity.empty())
                false, null -> Either.Left(Failure.NetworkConnection)
            }
        }

        override fun register(params: PostRegisterAuthUseCase.Params): Either<Failure, RegisterAuthEntity> {
            return when (networkHandler.isConnected) {
                true -> request(service.register(params), {
                    it
                }, RegisterAuthEntity.empty())
                false, null -> Either.Left(Failure.NetworkConnection)
            }
        }

        override fun resetPassword(params: PostResetPasswordUseCase.Params): Either<Failure, ResetPasswordEntity> {
            return when (networkHandler.isConnected) {
                true -> request(service.resetPassword(params), {
                    it
                }, ResetPasswordEntity.empty())
                false, null -> Either.Left(Failure.NetworkConnection)
            }
        }

        override fun resetPasswordConfirm(params: PostResetPasswordConfirmUseCase.Params): Either<Failure, ResetPasswordConfirmEntity> {
            return when (networkHandler.isConnected) {
                true -> request(service.resetPasswordConfirm(params), {
                    it
                }, ResetPasswordConfirmEntity.empty())
                false, null -> Either.Left(Failure.NetworkConnection)
            }
        }
    }
}