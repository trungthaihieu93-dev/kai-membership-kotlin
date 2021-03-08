package com.kardia.membership.domain.api

import com.kardia.membership.domain.entities.device.PasscodeDeviceEntity
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

internal interface DeviceAPI {
    companion object {
        private const val DEVICE_ID = "deviceId"
        private const val GET_PASSCODE_BY_DEVICE = "v1/devices/{${DEVICE_ID}}"
    }

    @GET(GET_PASSCODE_BY_DEVICE)
    fun getPasscodeByDevice(@Path(DEVICE_ID) deviceId: String): Call<PasscodeDeviceEntity>

}