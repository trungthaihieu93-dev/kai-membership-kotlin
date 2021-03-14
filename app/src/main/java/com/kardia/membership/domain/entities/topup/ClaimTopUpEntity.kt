package com.kardia.membership.domain.entities.topup

import com.kardia.membership.domain.entities.BaseEntities

data class ClaimTopUpEntity(val data: String?) :
    BaseEntities() {
    companion object {
        fun empty() = ClaimTopUpEntity("")
    }
}