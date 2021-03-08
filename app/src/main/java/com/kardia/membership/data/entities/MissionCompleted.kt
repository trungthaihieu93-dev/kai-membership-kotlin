package com.kardia.membership.data.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MissionCompleted (
    val completedDate: String?,
    val createdDate: String?,
    val _id: String?,
    val description: String?,
    val questions: List<Question>?,
    val status: String?,
    val title: String?
): Parcelable