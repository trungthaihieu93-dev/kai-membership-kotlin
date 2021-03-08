package com.kardia.membership.data.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PasscodeDevice(val device_id: String = "", val user: List<User>? = null) : Parcelable {
    companion object {
        fun empty() = PasscodeDevice("", null)
    }
}