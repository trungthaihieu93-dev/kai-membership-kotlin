package com.kardia.membership.domain.usecases.device

import com.kardia.membership.core.exception.Failure
import com.kardia.membership.core.functional.Either
import com.kardia.membership.core.interactor.UseCase
import com.kardia.membership.domain.entities.device.PasscodeDeviceEntity
import com.kardia.membership.domain.repositories.DeviceRepository
import javax.inject.Inject

class GetPasscodeDeviceUseCase @Inject constructor(private val repository: DeviceRepository) :
    UseCase<PasscodeDeviceEntity, String>() {

    override suspend fun run(params: String): Either<Failure, PasscodeDeviceEntity> {
        return repository.getPasscodeByDevice(params)
    }
}