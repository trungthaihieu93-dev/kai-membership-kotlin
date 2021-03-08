package com.kardia.membership.domain.usecases.transaction

import com.kardia.membership.core.exception.Failure
import com.kardia.membership.core.functional.Either
import com.kardia.membership.core.interactor.UseCase
import com.kardia.membership.domain.entities.transaction.TransactionsEntity
import com.kardia.membership.domain.repositories.TransactionRepository
import javax.inject.Inject

class GetTransactionsUseCase
@Inject constructor(private val repository: TransactionRepository) :
    UseCase<TransactionsEntity, String>() {

    override suspend fun run(params: String): Either<Failure, TransactionsEntity> {
        return repository.getTransactions()
    }
}