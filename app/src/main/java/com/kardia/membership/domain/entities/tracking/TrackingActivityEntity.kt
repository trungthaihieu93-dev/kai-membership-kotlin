package com.kardia.membership.domain.entities.tracking

import com.kardia.membership.domain.entities.BaseEntities

data class TrackingActivityEntity(val data: String?) :
    BaseEntities() {
    companion object {
        fun empty() = TrackingActivityEntity(null)
    }
}