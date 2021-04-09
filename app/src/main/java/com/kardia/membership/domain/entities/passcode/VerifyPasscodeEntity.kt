package com.kardia.membership.domain.entities.passcode

import com.kardia.membership.domain.entities.BaseEntities

data class VerifyPasscodeEntity(val data: String?) :
    BaseEntities() {
    companion object {
        fun empty() = VerifyPasscodeEntity(null)
    }
}