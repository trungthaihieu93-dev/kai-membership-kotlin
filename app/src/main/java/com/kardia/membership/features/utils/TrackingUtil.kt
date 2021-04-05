package com.kardia.membership.features.utils

import android.content.Context
import com.appsflyer.AFInAppEventParameterName
import com.appsflyer.AppsFlyerLib
import com.kardia.membership.data.entities.User
import java.util.*
import kotlin.collections.HashMap

object TrackingUtil {
    const val COMPLETE_REGISTRATION = "af_complete_registration"
    const val START = "af_start"
    const val TOP_UP = "af_topup"

    fun tracking(type: String, objectItem: Any?, context: Context) {
        val eventValues = HashMap<String, Any>()
        when (type) {
            COMPLETE_REGISTRATION -> {
                val user = objectItem as User
                user._id?.let {
                    eventValues["af_user_id"] = it
                }
                user.email?.let {
                    eventValues["af_email"] = it
                }
                eventValues["af_time"] = Date().time
            }
            TOP_UP -> {
                val user = objectItem as User
                user._id?.let {
                    eventValues["af_user_id"] = it
                }
            }
        }
        AppsFlyerLib.getInstance().logEvent(context, type, eventValues)
    }
}