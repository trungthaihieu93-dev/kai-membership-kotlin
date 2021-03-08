package com.kardia.membership.domain.repositories

import com.kardia.membership.core.exception.Failure
import com.kardia.membership.core.functional.Either
import com.kardia.membership.core.platform.NetworkHandler
import com.kardia.membership.domain.entities.device.PasscodeDeviceEntity
import com.kardia.membership.domain.network.BaseNetwork
import com.kardia.membership.domain.services.DeviceService
import javax.inject.Inject

interface DeviceRepository {
    fun getPasscodeByDevice(deviceId:String): Either<Failure, PasscodeDeviceEntity>

    class Network
    @Inject constructor(
        private val networkHandler: NetworkHandler,
        private val service: DeviceService
    ) : DeviceRepository, BaseNetwork() {
        override fun getPasscodeByDevice(deviceId:String): Either<Failure, PasscodeDeviceEntity> {
            return when (networkHandler.isConnected) {
                true -> request(service.getPasscodeByDevice(deviceId), {
                    it
                }, PasscodeDeviceEntity.empty())
                false, null -> Either.Left(Failure.NetworkConnection)
            }
        }
    }
}