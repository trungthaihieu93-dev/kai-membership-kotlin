package com.kardia.membership.core.extension

import java.util.*

fun Calendar.getStartOfDay(): Long {
    val calendar = this
    calendar.set(Calendar.HOUR_OF_DAY, 0)
    calendar.set(Calendar.MINUTE, 0)
    calendar.set(Calendar.SECOND, 0)

    return calendar.timeInMillis
}

fun Calendar.getEndOfDay(): Long {
    val calendar = this
    calendar.set(Calendar.HOUR_OF_DAY, 23)
    calendar.set(Calendar.MINUTE, 59)
    calendar.set(Calendar.SECOND, 59)

    return calendar.timeInMillis
}

fun Calendar.toEndOfMonth(): Calendar {
    set(Calendar.DAY_OF_MONTH, getActualMaximum(Calendar.DAY_OF_MONTH))
    return this
}