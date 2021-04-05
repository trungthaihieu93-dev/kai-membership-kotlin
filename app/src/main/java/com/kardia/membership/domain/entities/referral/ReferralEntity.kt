package com.kardia.membership.domain.entities.referral

import com.kardia.membership.domain.entities.BaseEntities

data class ReferralEntity(val data: String? = null) :
    BaseEntities() {
    companion object {
        fun empty() = ReferralEntity(null)
    }
}