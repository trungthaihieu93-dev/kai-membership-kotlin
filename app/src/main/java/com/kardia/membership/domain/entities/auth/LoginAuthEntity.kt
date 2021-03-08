package com.kardia.membership.domain.entities.auth

import com.kardia.membership.data.entities.Login
import com.kardia.membership.domain.entities.BaseEntities

data class LoginAuthEntity(val data: Login?) :
    BaseEntities() {
    companion object {
        fun empty() = LoginAuthEntity(null)
    }
}