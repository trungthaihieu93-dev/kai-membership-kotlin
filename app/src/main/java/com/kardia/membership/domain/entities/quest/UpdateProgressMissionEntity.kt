package com.kardia.membership.domain.entities.quest

import com.kardia.membership.data.entities.UpdateProgressMission
import com.kardia.membership.domain.entities.BaseEntities

data class UpdateProgressMissionEntity(val data: UpdateProgressMission?) :
    BaseEntities() {
    companion object {
        fun empty() = UpdateProgressMissionEntity(UpdateProgressMission.empty())
    }
}