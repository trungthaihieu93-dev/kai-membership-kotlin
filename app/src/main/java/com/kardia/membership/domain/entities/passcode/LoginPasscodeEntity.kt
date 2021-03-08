package com.kardia.membership.domain.entities.passcode

import com.kardia.membership.data.entities.Login
import com.kardia.membership.domain.entities.BaseEntities

data class LoginPasscodeEntity(val data: Login?) :
    BaseEntities() {
    companion object {
        fun empty() = LoginPasscodeEntity(null)
    }
}