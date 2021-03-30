package com.kardia.membership.features.viewmodel

import androidx.lifecycle.MutableLiveData
import com.kardia.membership.core.platform.BaseViewModel
import com.kardia.membership.domain.entities.news.FeatureNewsEntity
import com.kardia.membership.domain.entities.news.LatestNewsEntity
import com.kardia.membership.domain.usecases.news.GetFeatureNewsUseCase
import com.kardia.membership.domain.usecases.news.GetLatestNewsUseCase
import javax.inject.Inject

class NewsViewModel
@Inject constructor(
    private val getFeatureNewsUseCase: GetFeatureNewsUseCase,
    private val getLatestNewsUseCase: GetLatestNewsUseCase
) : BaseViewModel() {
    var featureNewsEntity: MutableLiveData<FeatureNewsEntity> = MutableLiveData()
    var latestNewsEntity: MutableLiveData<LatestNewsEntity> = MutableLiveData()

    fun getFeatureNews() = getFeatureNewsUseCase("") {
        it.fold(::handleFailure, ::handleDataFeatureNews)
    }

    private fun handleDataFeatureNews(data: FeatureNewsEntity) {
        this.featureNewsEntity.value = data
    }

    fun getLatestNews() = getLatestNewsUseCase("") {
        it.fold(::handleFailure, ::handleDataLatestNews)
    }

    private fun handleDataLatestNews(data: LatestNewsEntity) {
        this.latestNewsEntity.value = data
    }
}