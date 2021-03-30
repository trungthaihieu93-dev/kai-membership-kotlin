package com.kardia.membership.domain.entities.news

import com.kardia.membership.data.entities.NewsFeature
import com.kardia.membership.domain.entities.BaseEntities

data class FeatureNewsEntity(val items: List<NewsFeature> = ArrayList()) :
    BaseEntities() {
    companion object {
        fun empty() = FeatureNewsEntity(ArrayList())
    }
}