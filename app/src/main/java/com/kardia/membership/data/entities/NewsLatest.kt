package com.kardia.membership.data.entities

data class NewsLatest(
    val created_at: String?,
    val id: Long?,
    val id_str: String?,
    val full_text: String?,
    val truncated: Boolean?,
    val display_text_range: List<Int>?,
    val entities: Entity?,
    val extended_entities: Entity?,
    val metadata: Metadata?,
    val source: String?,
    val user: UserNews?,
    val is_quote_status: Boolean?,
    val retweet_count: Int?,
    val favorite_count: Int?,
    val favorited: Boolean?,
    val retweeted: Boolean?,
    val possibly_sensitive: Boolean?,
    val lang: String?
)