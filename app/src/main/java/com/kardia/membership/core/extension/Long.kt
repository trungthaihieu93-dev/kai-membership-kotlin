package com.kardia.membership.core.extension

import java.text.SimpleDateFormat
import java.util.*

fun Long.getMonthYearDate(pattern: String): String {
    if (this == 0L) return ""
    return SimpleDateFormat(pattern, Locale.getDefault()).format(Date(this ))
}

fun Long.convertMonthToMilliseconds(): Long {
    return (this)
}