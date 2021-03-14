package com.kardia.membership.core.extension

import android.text.SpannableStringBuilder
import com.kardia.membership.features.utils.AppConstants
import java.text.NumberFormat
import java.util.*

fun Float.formatThousand(): String {
    val nf = NumberFormat.getInstance(Locale.US)
    nf.maximumFractionDigits = 4
    return nf.format(this)
}

fun Float.formatKAI(): String {
    val nf = NumberFormat.getInstance(Locale.US)
    nf.maximumFractionDigits = 4
    return String.format("%s %s",nf.format(this),AppConstants.UNIT_KAI)
}