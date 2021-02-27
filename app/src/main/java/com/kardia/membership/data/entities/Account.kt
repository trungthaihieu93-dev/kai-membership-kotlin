package com.kardia.membership.data.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize

data class Account(
    val id: Int = 0,
    val name: String? = null,
    val avatar: String? = null,
    val email: String? = null
) :
    Parcelable {
    companion object {
        fun empty() =
            Account(
                0
            )
    }
}