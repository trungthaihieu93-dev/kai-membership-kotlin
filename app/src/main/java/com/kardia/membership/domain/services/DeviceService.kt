package com.kardia.membership.domain.services

import com.kardia.membership.domain.api.DeviceAPI
import com.kardia.membership.domain.entities.device.PasscodeDeviceEntity
import retrofit2.Call
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeviceService
@Inject constructor(retrofit: Retrofit) : DeviceAPI {
    private val deviceAPI by lazy {
        retrofit.create(DeviceAPI::class.java)
    }

    override fun getPasscodeByDevice(deviceId: String): Call<PasscodeDeviceEntity> {
        return deviceAPI.getPasscodeByDevice(deviceId)
    }
}