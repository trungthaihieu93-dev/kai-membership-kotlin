package com.kardia.membership.features.viewmodel

import androidx.lifecycle.MutableLiveData
import com.kardia.membership.core.platform.BaseViewModel
import com.kardia.membership.domain.entities.config.ConfigEntity
import com.kardia.membership.domain.usecases.config.GetConfigUseCase
import javax.inject.Inject

class ConfigViewModel
@Inject constructor(
    private val getConfigUseCase: GetConfigUseCase
) : BaseViewModel() {
    var configEntity: MutableLiveData<ConfigEntity> = MutableLiveData()

    fun getConfig() = getConfigUseCase("") {
        it.fold(::handleFailure, ::handleConfig)
    }

    private fun handleConfig(data: ConfigEntity) {
        this.configEntity.value = data
    }
}