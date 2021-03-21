package com.kardia.membership.core.extension

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextUtils
import android.text.style.ForegroundColorSpan

import com.kardia.membership.features.utils.AppConstants
import java.text.DateFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern


fun String.Companion.empty() = ""

val Any.TAG: String
    get() {
        val tag = javaClass.simpleName
        return if (tag.length <= 23) tag else tag.substring(0, 23)
    }

fun String.getCalendarInstance(pattern: String): Calendar {
    val cal = Calendar.getInstance()
    val day = cal.get(Calendar.DAY_OF_MONTH)
    val sdf =
        SimpleDateFormat(pattern, Locale.getDefault())
    if (!TextUtils.isEmpty(this)) {
        cal.time = sdf.parse(this) // all done
    } else {
        cal.set(Calendar.DAY_OF_MONTH, day - 1)
    }
    return cal
}

fun String.isValidVNPhoneNumber(): Boolean {
    val regex = "(03|05|07|08|09|01[2|6|8|9])+([0-9]{8})\\b"

    val pattern: Pattern = Pattern.compile(regex, Pattern.MULTILINE)
    val matcher: Matcher = pattern.matcher(this)
    return matcher.matches()
}

fun String.appendRed(): SpannableStringBuilder {
    val colored = " *"
    val builder = SpannableStringBuilder(this + colored)

    builder.setSpan(
        ForegroundColorSpan(Color.RED), this.length, builder.length,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    return builder
}

@SuppressLint("SimpleDateFormat")
fun String.getDateFormat(pattern: String): String {
    val df: DateFormat =
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    val date = df.parse(this)
    date?.let {
        return SimpleDateFormat(pattern, Locale.getDefault()).format(date)
    }
    return ""
}

fun String.formatThousand(): String {
    val nf = NumberFormat.getInstance(Locale.US)
    nf.maximumFractionDigits = 4
    return nf.format(this.toFloat())
}

fun String.formatPrice(): String {
    return String.format(
        "%s %s",
        NumberFormat.getNumberInstance(Locale.US).format(this.toFloat()),
        AppConstants.UNIT_PRICE
    )
}