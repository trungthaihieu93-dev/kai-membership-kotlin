package com.kardia.membership.domain.entities.quest

import com.kardia.membership.domain.entities.BaseEntities

data class CheckProgressMissionEntity(val data: Boolean?) :
    BaseEntities() {
    companion object {
        fun empty() = CheckProgressMissionEntity(false)
    }
}