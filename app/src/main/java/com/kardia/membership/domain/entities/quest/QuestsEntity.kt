package com.kardia.membership.domain.entities.quest

import com.kardia.membership.data.entities.Quest
import com.kardia.membership.domain.entities.BaseEntities

data class QuestsEntity(val data: List<Quest> = ArrayList()) :
    BaseEntities() {
    companion object {
        fun empty() = QuestsEntity(ArrayList())
    }
}