package com.kardia.membership.features.utils

import android.content.Context
import android.webkit.MimeTypeMap
import com.kardia.membership.core.platform.BaseActivity
import com.kardia.membership.features.dialog.DialogAlert

object CommonUtils {
    fun showError(context: Context?, title: String?, message: String?) {
        if (context == null) return
        DialogAlert()
                .setTitle(title ?: "ServerError")
                .setMessage(message ?: "")
                .setCancel(false)
                .setTitlePositive("Đồng ý")
                .onDismiss { (context as BaseActivity).isShowError = false }
                .show(context)
    }

    fun getMimeType(url: String?): String? {
        var type: String? = null
        val extension = MimeTypeMap.getFileExtensionFromUrl(url)
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
        }
        return type
    }

    fun getBeautyDate(month: Int, dayOfMonth: Int, year: Int): String {
        var monthText = if (month + 1 < 10) "0${month + 1}" else "${month + 1}"
        var dayOfMonthText = if (dayOfMonth < 10) "0$dayOfMonth" else "$dayOfMonth"
        return "$dayOfMonthText/$monthText/$year"
    }

    fun getRemainingTime(date: Long, limit: Int = 14): String {
        val seconds = limit * 24 * 60 * 60 - (System.currentTimeMillis() / 1000 - date)
        if (seconds <= 0) return "0 day"
        val sec: Long = seconds % 60
        val minutes: Long = seconds % 3600 / 60
        val hours: Long = seconds % 86400 / 3600
        val days: Long = seconds / 86400

        var result = ""
        if (days > 0) result += "$days days"

        if (hours > 0) {
            if (result.isNotEmpty()) result += " "
            result += "$hours hours"
        }
        if (days == 0L && hours == 0L) result = "$minutes minutes $sec seconds"
        return result
    }
}