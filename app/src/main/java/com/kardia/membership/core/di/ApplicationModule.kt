package com.kardia.membership.core.di

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.kardia.membership.BuildConfig
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.itkacher.okhttpprofiler.OkHttpProfilerInterceptor
import com.kardia.membership.AndroidApplication
import com.kardia.membership.data.cache.UserTokenCache
import com.kardia.membership.domain.network.ServiceInterceptor
import com.kardia.membership.domain.repositories.*
import com.readystatesoftware.chuck.ChuckInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
class ApplicationModule(private val application: AndroidApplication) {
    @Provides
    @Singleton
    fun provideApplicationContext(): Context = application

    @Provides
    @Singleton
    fun provideAndroidApplication(): AndroidApplication = application


    @Provides
    @Singleton
    fun provideRetrofit(userTokenCache: UserTokenCache, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://membership-backend.kardiachain.io/api/")
            .client(createClient(userTokenCache))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder()
            .setLenient()
            .create()
    }

    @Provides
    @Singleton
    fun provideSharePreference(): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(application)
    }

    private fun createClient(userTokenCache: UserTokenCache): OkHttpClient {
        val okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
            .readTimeout(15000, TimeUnit.MILLISECONDS)
            .connectTimeout(15000, TimeUnit.MILLISECONDS)
            .writeTimeout(15000, TimeUnit.MILLISECONDS)
        okHttpClientBuilder.addInterceptor(ServiceInterceptor(userTokenCache))
        okHttpClientBuilder.addInterceptor(ChuckInterceptor(application))
        if (BuildConfig.DEBUG) {
            val loggingInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            okHttpClientBuilder.addInterceptor(loggingInterceptor)
            okHttpClientBuilder.addInterceptor(OkHttpProfilerInterceptor())
        }
        return okHttpClientBuilder.build()
    }

    @Provides
    @Singleton
    fun provideDeviceRepository(dataSource: DeviceRepository.Network): DeviceRepository =
        dataSource

    @Provides
    @Singleton
    fun provideAuthRepository(dataSource: AuthRepository.Network): AuthRepository =
        dataSource

    @Provides
    @Singleton
    fun providePasscodeRepository(dataSource: PasscodeRepository.Network): PasscodeRepository =
        dataSource

    @Provides
    @Singleton
    fun provideUserRepository(dataSource: UserRepository.Network): UserRepository =
        dataSource

    @Provides
    @Singleton
    fun provideTransactionRepository(dataSource: TransactionRepository.Network): TransactionRepository =
        dataSource

    @Provides
    @Singleton
    fun provideConfigRepository(dataSource: ConfigRepository.Network): ConfigRepository =
        dataSource

    @Provides
    @Singleton
    fun provideTopUpRepository(dataSource: TopUpRepository.Network): TopUpRepository =
        dataSource

    @Provides
    @Singleton
    fun provideQuestRepository(dataSource: QuestRepository.Network): QuestRepository =
        dataSource

    @Provides
    @Singleton
    fun provideTrackingRepository(dataSource: TrackingRepository.Network): TrackingRepository =
        dataSource

    @Provides
    @Singleton
    fun provideCaptchaRepository(dataSource: CaptchaRepository.Network): CaptchaRepository =
        dataSource

    @Provides
    @Singleton
    fun provideNewsRepository(dataSource: NewsRepository.Network): NewsRepository =
        dataSource

    @Provides
    @Singleton
    fun provideTwitterRepository(dataSource: TwitterRepository.Network): TwitterRepository =
        dataSource

    @Provides
    @Singleton
    fun provideWalletRepository(dataSource: WalletRepository.Network): WalletRepository =
        dataSource

    @Provides
    @Singleton
    fun provideReferralRepository(dataSource: ReferralRepository.Network): ReferralRepository =
        dataSource
}
