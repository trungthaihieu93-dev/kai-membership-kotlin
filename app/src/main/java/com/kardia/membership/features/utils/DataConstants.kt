package com.kardia.membership.features.utils

import com.kardia.membership.data.entities.TopUpAmount
import com.kardia.membership.domain.entities.transaction.TransactionsEntity

object DataConstants {
    var TRANSACTION_ENTITY: TransactionsEntity? = null
    var TOP_UP_AMOUNT_LIST = ArrayList<TopUpAmount>()
}