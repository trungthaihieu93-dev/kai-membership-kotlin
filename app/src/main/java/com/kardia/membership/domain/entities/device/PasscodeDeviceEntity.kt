package com.kardia.membership.domain.entities.device

import com.kardia.membership.data.entities.PasscodeDevice
import com.kardia.membership.domain.entities.BaseEntities

data class PasscodeDeviceEntity(val data: PasscodeDevice = PasscodeDevice.empty()) :
    BaseEntities() {
    companion object {
        fun empty() = PasscodeDeviceEntity(PasscodeDevice.empty())
    }
}