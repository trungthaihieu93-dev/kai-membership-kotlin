package com.kardia.membership.domain.entities.auth

import com.kardia.membership.data.entities.Register
import com.kardia.membership.domain.entities.BaseEntities

data class RegisterAuthEntity(val data: Register?) :
    BaseEntities() {
    companion object {
        fun empty() = RegisterAuthEntity(null)
    }
}