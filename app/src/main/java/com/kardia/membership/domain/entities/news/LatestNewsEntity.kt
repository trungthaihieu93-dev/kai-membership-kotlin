package com.kardia.membership.domain.entities.news

import com.kardia.membership.data.entities.NewsLatest
import com.kardia.membership.domain.entities.BaseEntities

data class LatestNewsEntity(val statuses: List<NewsLatest> = ArrayList()) :
    BaseEntities() {
    companion object {
        fun empty() = LatestNewsEntity(ArrayList())
    }
}