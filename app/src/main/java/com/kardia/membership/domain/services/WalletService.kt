package com.kardia.membership.domain.services

import com.kardia.membership.domain.api.WalletAPI
import com.kardia.membership.domain.entities.wallet.SendKAIEntity
import com.kardia.membership.domain.usecases.wallet.PostSendKAIUseCase
import retrofit2.Call
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WalletService
@Inject constructor(retrofit: Retrofit) : WalletAPI {
    private val walletAPI by lazy {
        retrofit.create(WalletAPI::class.java)
    }

    override fun sendKAI(params: PostSendKAIUseCase.Params): Call<SendKAIEntity> {
        return walletAPI.sendKAI(params)
    }

}