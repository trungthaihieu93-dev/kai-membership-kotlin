package com.kardia.membership.domain.api

import com.kardia.membership.domain.entities.transaction.TransactionsEntity
import retrofit2.Call
import retrofit2.http.GET

internal interface TransactionAPI {
    companion object {
        private const val GET_TRANSACTION_LIST = "v1/transactions"
    }

    @GET(GET_TRANSACTION_LIST)
    fun getTransactions(): Call<TransactionsEntity>

}