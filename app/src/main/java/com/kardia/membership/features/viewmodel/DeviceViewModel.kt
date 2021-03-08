package com.kardia.membership.features.viewmodel

import androidx.lifecycle.MutableLiveData
import com.kardia.membership.core.platform.BaseViewModel
import com.kardia.membership.domain.entities.device.PasscodeDeviceEntity
import com.kardia.membership.domain.usecases.device.GetPasscodeDeviceUseCase
import javax.inject.Inject

class DeviceViewModel
@Inject constructor(
    private val getPasscodeDeviceUseCase: GetPasscodeDeviceUseCase
) : BaseViewModel() {
    var passcodeDeviceEntity: MutableLiveData<PasscodeDeviceEntity> = MutableLiveData()

    fun getPasscodeByDevice(deviceId:String) = getPasscodeDeviceUseCase(deviceId) {
        it.fold(::handleFailure, ::handleDataPasscodeDevice)
    }

    private fun handleDataPasscodeDevice(data: PasscodeDeviceEntity) {
        this.passcodeDeviceEntity.value = data
    }
}