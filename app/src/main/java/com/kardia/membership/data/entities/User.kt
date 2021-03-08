package com.kardia.membership.data.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val _id: String?,
    val username: String?,
    val sex: String?,
    val birthday: String?,
    val createdDate: String?,
    val updatedDate: String?,
    val phone: String?,
    val avatar: String?,
    val active: Boolean?,
    val point: Long?,
    val missioncompleted: List<MissionCompleted>?,
    val spinedcount: Int?,
    val spinturn: Int?,
    val customerID: Long?,
    val spinperday: Int?,
    val spinday: String?,
    val spinmax: Boolean?,
    val last_login: String?,
    val os: String?,
    val isUpdated: Boolean?,
    val email: String?,
    val rate_app: Int?,
    val invite_android: String?,
    val fcm_token: String?,
    val passcode: String?,
    val refreshToken: String?,
    val is_captcha: Boolean?,
    val is_verified: Boolean?,
    val refarral_appflyer_link: String?
) : Parcelable