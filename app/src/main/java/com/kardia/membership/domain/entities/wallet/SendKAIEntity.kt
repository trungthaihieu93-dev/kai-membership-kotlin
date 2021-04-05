package com.kardia.membership.domain.entities.wallet

import com.kardia.membership.domain.entities.BaseEntities

data class SendKAIEntity(val data: String? = null) :
    BaseEntities() {
    companion object {
        fun empty() = SendKAIEntity(null)
    }
}