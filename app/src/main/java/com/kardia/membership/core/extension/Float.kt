package com.kardia.membership.core.extension

import android.text.SpannableStringBuilder
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

fun Float.formatKAI(): SpannableStringBuilder {
    return SpannableStringBuilder(
        NumberFormat.getNumberInstance(Locale.GERMANY).format(this)
    )
}