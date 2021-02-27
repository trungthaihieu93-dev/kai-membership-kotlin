package com.kardia.membership.features.utils

import android.util.Log
import com.kardia.membership.BuildConfig

class AppLog {
    companion object {
        @JvmStatic
        fun e(tag: String, message: String) {
            if (BuildConfig.DEBUG) Log.e(tag, message)
        }

        fun e(tag: String, message: String, s: Throwable) {
            if (BuildConfig.DEBUG) Log.e(tag, message, s)
        }

        fun d(tag: String, message: String) {
            if (BuildConfig.DEBUG) Log.d(tag, message)
        }
    }
}