package com.kardia.membership.domain.repositories

import com.kardia.membership.core.exception.Failure
import com.kardia.membership.core.functional.Either
import com.kardia.membership.core.platform.NetworkHandler
import com.kardia.membership.domain.entities.device.PasscodeDeviceEntity
import com.kardia.membership.domain.entities.passcode.*
import com.kardia.membership.domain.network.BaseNetwork
import com.kardia.membership.domain.services.PasscodeService
import com.kardia.membership.domain.usecases.passcode.*
import javax.inject.Inject

interface PasscodeRepository {
    fun login(params: PostLoginPasscodeUseCase.Params): Either<Failure, LoginPasscodeEntity>
    fun register(params: PostRegisterPasscodeUseCase.Params): Either<Failure, RegisterPasscodeEntity>
    fun check(params: PostCheckPasscodeUseCase.Params): Either<Failure, CheckPasscodeEntity>
    fun forgot(params: PostForgotPasscodeUseCase.Params): Either<Failure, ForgotPasscodeEntity>
    fun reset(params: PostResetPasscodeUseCase.Params): Either<Failure, ResetPasscodeEntity>
    class Network
    @Inject constructor(
        private val networkHandler: NetworkHandler,
        private val service: PasscodeService
    ) : PasscodeRepository, BaseNetwork() {
        override fun login(params: PostLoginPasscodeUseCase.Params): Either<Failure, LoginPasscodeEntity> {
            return when (networkHandler.isConnected) {
                true -> request(service.login(params), {
                    it
                }, LoginPasscodeEntity.empty())
                false, null -> Either.Left(Failure.NetworkConnection)
            }
        }

        override fun register(params: PostRegisterPasscodeUseCase.Params): Either<Failure, RegisterPasscodeEntity> {
            return when (networkHandler.isConnected) {
                true -> request(service.register(params), {
                    it
                }, RegisterPasscodeEntity.empty())
                false, null -> Either.Left(Failure.NetworkConnection)
            }
        }

        override fun check(params: PostCheckPasscodeUseCase.Params): Either<Failure, CheckPasscodeEntity> {
            return when (networkHandler.isConnected) {
                true -> request(service.check(params), {
                    it
                }, CheckPasscodeEntity.empty())
                false, null -> Either.Left(Failure.NetworkConnection)
            }
        }

        override fun forgot(params: PostForgotPasscodeUseCase.Params): Either<Failure, ForgotPasscodeEntity> {
            return when (networkHandler.isConnected) {
                true -> request(service.forgot(params), {
                    it
                }, ForgotPasscodeEntity.empty())
                false, null -> Either.Left(Failure.NetworkConnection)
            }
        }

        override fun reset(params: PostResetPasscodeUseCase.Params): Either<Failure, ResetPasscodeEntity> {
            return when (networkHandler.isConnected) {
                true -> request(service.reset(params), {
                    it
                }, ResetPasscodeEntity.empty())
                false, null -> Either.Left(Failure.NetworkConnection)
            }
        }
    }
}