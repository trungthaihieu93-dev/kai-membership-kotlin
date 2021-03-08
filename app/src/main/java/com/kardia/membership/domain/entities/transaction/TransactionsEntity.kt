package com.kardia.membership.domain.entities.transaction

import com.kardia.membership.data.entities.Transaction
import com.kardia.membership.data.entities.TransactionItem
import com.kardia.membership.domain.entities.BaseEntities

data class TransactionsEntity(val data: List<TransactionItem> = ArrayList()) :
    BaseEntities() {
    companion object {
        fun empty() = TransactionsEntity(ArrayList())
    }
}