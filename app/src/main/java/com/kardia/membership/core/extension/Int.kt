package com.kardia.membership.core.extension

import java.text.NumberFormat
import java.util.*

fun Int.minToSecond(): Int = this * 60

fun Int.displayDayOfWeek(): String{
    return when (this){
        1 -> "Chủ Nhật"
        2 -> "Thứ 2"
        3 -> "Thứ 3"
        4 -> "Thứ 4"
        5 -> "Thứ 5"
        6 -> "Thứ 6"
        else -> "Thứ 7"
    }
}

fun Int.formatThousand(): String {
    val nf = NumberFormat.getInstance(Locale.US)
    nf.maximumFractionDigits = 4
    return nf.format(this.toFloat())
}
