package com.kardia.membership.data.entities

data class Entity(
    val hashtags: List<HashTag>?,
    val symbols: List<Symbol>?,
    val user_mentions: List<UserMention>?,
    val urls: List<Url>?,
    val media:List<Media>?
)