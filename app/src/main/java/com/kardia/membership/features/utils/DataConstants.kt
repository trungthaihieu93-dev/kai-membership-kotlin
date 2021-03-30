package com.kardia.membership.features.utils

import com.kardia.membership.data.entities.Quest
import com.kardia.membership.data.entities.TopUpAmount
import com.kardia.membership.domain.entities.news.FeatureNewsEntity
import com.kardia.membership.domain.entities.news.LatestNewsEntity
import com.kardia.membership.domain.entities.quest.QuestsEntity
import com.kardia.membership.domain.entities.transaction.TransactionsEntity

object DataConstants {
    var TRANSACTION_ENTITY: TransactionsEntity? = null
    var TOP_UP_AMOUNT_LIST = ArrayList<TopUpAmount>()
    var QUEST_ENTITY: QuestsEntity? = null
    var FEATURE_NEWS_ENTITY: FeatureNewsEntity? = null
    var LATEST_NEWS_ENTITY: LatestNewsEntity? = null
}