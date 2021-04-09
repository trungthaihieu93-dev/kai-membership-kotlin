package com.kardia.membership.data.entities

data class Login(
    val access_token: String?,
    val refresh_token: String?,
    val expires_in: Long?,
    val is_first: Boolean?,
    val has_passcode:Boolean?
)