package com.kardia.membership.domain.entities.passcode

import com.kardia.membership.data.entities.Register
import com.kardia.membership.domain.entities.BaseEntities

class RegisterPasscodeEntity(val data: Register?) :
    BaseEntities() {
    companion object {
        fun empty() = RegisterPasscodeEntity(null)
    }
}