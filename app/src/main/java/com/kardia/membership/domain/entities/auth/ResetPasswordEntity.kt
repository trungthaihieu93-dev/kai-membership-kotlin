package com.kardia.membership.domain.entities.auth

import com.kardia.membership.domain.entities.BaseEntities

class ResetPasswordEntity(val data: String?) :
    BaseEntities() {
    companion object {
        fun empty() = ResetPasswordEntity(null)
    }
}