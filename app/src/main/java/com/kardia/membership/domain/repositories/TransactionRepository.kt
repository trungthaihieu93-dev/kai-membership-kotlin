package com.kardia.membership.domain.repositories

import com.kardia.membership.core.exception.Failure
import com.kardia.membership.core.functional.Either
import com.kardia.membership.core.platform.NetworkHandler
import com.kardia.membership.domain.entities.transaction.TransactionsEntity
import com.kardia.membership.domain.network.BaseNetwork
import com.kardia.membership.domain.services.TransactionService
import javax.inject.Inject

interface TransactionRepository {
    fun getTransactions(): Either<Failure, TransactionsEntity>

    class Network
    @Inject constructor(
        private val networkHandler: NetworkHandler,
        private val service: TransactionService
    ) : TransactionRepository, BaseNetwork() {
        override fun getTransactions(): Either<Failure, TransactionsEntity> {
            return when (networkHandler.isConnected) {
                true -> request(service.getTransactions(), {
                    it
                }, TransactionsEntity.empty())
                false, null -> Either.Left(Failure.NetworkConnection)
            }
        }
    }
}