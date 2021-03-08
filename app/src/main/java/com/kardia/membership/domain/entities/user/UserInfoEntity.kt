package com.kardia.membership.domain.entities.user

import com.kardia.membership.data.entities.UserInfo
import com.kardia.membership.domain.entities.BaseEntities

data class UserInfoEntity(val data: UserInfo?) :
    BaseEntities() {
    companion object {
        fun empty() = UserInfoEntity(null)
    }
}