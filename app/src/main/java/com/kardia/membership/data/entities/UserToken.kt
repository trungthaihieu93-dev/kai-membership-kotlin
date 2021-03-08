package com.kardia.membership.data.entities

data class UserToken(
    val accessToken: String? = null,
    val refreshToken: String? = null,
    val expiresIn: Long? = 0
)
