package com.kardia.membership.domain.entities

open class BaseEntities {
    val isSuccess = false
    val errorCode = 200
    val error = ""

    companion object {
        fun empty() = BaseEntities()
    }

}