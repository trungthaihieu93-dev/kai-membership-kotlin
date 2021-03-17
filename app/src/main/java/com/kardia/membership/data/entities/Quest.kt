package com.kardia.membership.data.entities

data class Quest(
    val _id: String?,
    val created_date: String?,
    val updated_date: String?,
    val mission: String?,
    val screen_name: String?,
    val key: String?,
    val type: String?,
    val process: Int?,
    val status: Boolean?,
    val description :String?,
    var processing: Int?
)