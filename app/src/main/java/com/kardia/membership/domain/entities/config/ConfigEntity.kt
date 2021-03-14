package com.kardia.membership.domain.entities.config

import com.kardia.membership.data.entities.Config
import com.kardia.membership.domain.entities.BaseEntities

data class ConfigEntity(val data: List<Config> = ArrayList()) :
    BaseEntities() {
    companion object {
        fun empty() = ConfigEntity(ArrayList())
    }
}