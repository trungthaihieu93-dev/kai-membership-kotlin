package com.kardia.membership.domain.services

import com.kardia.membership.domain.api.TransactionAPI
import com.kardia.membership.domain.entities.transaction.TransactionsEntity
import retrofit2.Call
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransactionService
@Inject constructor(retrofit: Retrofit) : TransactionAPI {
    private val transactionAPI by lazy {
        retrofit.create(TransactionAPI::class.java)
    }

    override fun getTransactions(): Call<TransactionsEntity> {
        return transactionAPI.getTransactions()
    }
}