package com.kardia.membership.domain.entities.user

import com.kardia.membership.data.entities.History
import com.kardia.membership.domain.entities.BaseEntities

data class HistoryEntity(val data: List<History> = ArrayList()) :
    BaseEntities() {
    companion object {
        fun empty() = HistoryEntity(ArrayList())
    }
}