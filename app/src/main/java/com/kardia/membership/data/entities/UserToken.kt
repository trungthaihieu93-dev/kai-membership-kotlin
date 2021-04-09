package com.kardia.membership.data.entities

data class UserToken(
    val accessToken: String? = null,
    val refreshToken: String? = null,
    val expiresIn: Long? = 0,
    val isFirst :Boolean? = false,
    val hasPasscode:Boolean? = true
)
