package com.kardia.membership.data.entities

data class UserToken(
    val userID: Int? = null,
    var accessToken: String? = null,
    val refreshToken: String? = null,
    val expire_date: Long = 0
)
