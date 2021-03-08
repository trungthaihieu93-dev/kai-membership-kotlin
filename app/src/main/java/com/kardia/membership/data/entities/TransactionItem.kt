package com.kardia.membership.data.entities

data class TransactionItem(
    val _id: String?,
    val createdDate: String?,
    val updatedDate: String?,
    val history_id: String?,
    val value: String?,
    val email: String?,
    val wallet_receive: String?,
    val wallet_send: String?,
    val status: String?,
    val type: String?,
    val category: String?
)