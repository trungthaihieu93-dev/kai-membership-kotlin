package com.kardia.membership.domain.entities.auth

import com.kardia.membership.domain.entities.BaseEntities

data class ResetPasswordConfirmEntity(val data: String?) :
    BaseEntities() {
    companion object {
        fun empty() = ResetPasswordConfirmEntity(null)
    }
}