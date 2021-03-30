package com.kardia.membership.data.entities

data class UserMention(
    val screen_name: String?, val name: String?, val id: Long?,
    val id_str: String?, val indices: List<Int>?
)