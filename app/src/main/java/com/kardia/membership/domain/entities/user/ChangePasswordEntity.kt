package com.kardia.membership.domain.entities.user

import com.kardia.membership.domain.entities.BaseEntities

data class ChangePasswordEntity(val data: String?) :
    BaseEntities() {
    companion object {
        fun empty() = ChangePasswordEntity(null)
    }
}