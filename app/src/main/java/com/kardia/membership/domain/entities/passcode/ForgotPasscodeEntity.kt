package com.kardia.membership.domain.entities.passcode

import com.kardia.membership.domain.entities.BaseEntities

class ForgotPasscodeEntity(val data: String?) :
    BaseEntities() {
    companion object {
        fun empty() = ForgotPasscodeEntity(null)
    }
}