package com.kardia.membership.data.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TopUpAmount(val key:String?, val priceValue:String?, val KAIValue:String?,var isSelected:Boolean = false) :Parcelable