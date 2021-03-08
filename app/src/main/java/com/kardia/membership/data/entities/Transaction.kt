package com.kardia.membership.data.entities

data class Transaction(
    val date: String?,
    val transactionItems: List<TransactionItem>?
)