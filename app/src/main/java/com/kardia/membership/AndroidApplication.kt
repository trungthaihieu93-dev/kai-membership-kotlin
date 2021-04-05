package com.kardia.membership

import android.annotation.SuppressLint
import android.app.Application
import android.content.Intent
import android.provider.Settings
import android.util.Log
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.appsflyer.deeplink.DeepLink
import com.appsflyer.deeplink.DeepLinkListener
import com.appsflyer.deeplink.DeepLinkResult
import com.google.gson.Gson
import com.kardia.membership.core.di.ApplicationComponent
import com.kardia.membership.core.di.ApplicationModule
import com.kardia.membership.core.di.DaggerApplicationComponent
import com.kardia.membership.features.utils.AppConstants


class AndroidApplication : Application() {
    val LOG_TAG = "AppsFlyer_DeepLink"
    val DL_ATTRS = "dl_attrs"

    companion object {
        fun isActivityVisible(): Boolean {
            return activityVisible
        }

        fun activityResumed() {
            activityVisible = true
        }

        fun activityPaused() {
            activityVisible = false
        }

        private var activityVisible = false
    }

    val appComponent: ApplicationComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .build()
    }

    private val runningActivities: ArrayList<Class<*>> = ArrayList()

    @SuppressLint("HardwareIds")
    override fun onCreate() {
        super.onCreate()
        this.injectMembers()
        initAppsFlyer()

        val deviceId: String = Settings.Secure.getString(
            contentResolver,
            Settings.Secure.ANDROID_ID
        )
        AppConstants.DEVICE_ID = deviceId
    }

    private fun injectMembers() = appComponent.inject(this)

    fun addThisActivityToRunningActivityies(cls: Class<*>?) {
        if (!runningActivities.contains(cls)) runningActivities.add(cls!!)
    }

    fun removeThisActivityFromRunningActivities(cls: Class<*>?) {
        if (runningActivities.contains(cls)) runningActivities.remove(cls)
    }

    fun isActivityInBackStack(cls: Class<*>?): Boolean {
        return runningActivities.contains(cls)
    }

    private fun initAppsFlyer() {
        val appsflyer = AppsFlyerLib.getInstance()
        appsflyer.setMinTimeBetweenSessions(0)
        appsflyer.setDebugLog(true)

        appsflyer.subscribeForDeepLink(DeepLinkListener { deepLinkResult ->
            when (deepLinkResult.status.name) {
                "FOUND" -> {
                    Log.d(LOG_TAG, "Deep link found")
                }
                "NOT_FOUND" -> {
                    Log.d(LOG_TAG, "Deep link not found")
                    return@DeepLinkListener
                }
                else -> {
                    // dlStatus == DeepLinkResult.Status.ERROR
                    val dlError = deepLinkResult.error
                    Log.d(LOG_TAG, "There was an error getting Deep Link data: $dlError")
                    return@DeepLinkListener
                }
            }
            val deepLinkObj = deepLinkResult.deepLink
            try {
                Log.d(LOG_TAG, "The DeepLink data is: $deepLinkObj")
            } catch (e: Exception) {
                Log.d(LOG_TAG, "DeepLink data came back null")
                return@DeepLinkListener
            }
            // An example for using is_deferred
            deepLinkObj.isDeferred?.let{
                if (it) {
                    Log.d(LOG_TAG, "This is a deferred deep link")
                } else {
                    Log.d(LOG_TAG, "This is a direct deep link")
                }
            }
            deepLinkObj.getStringValue(AppConstants.KEY_USER_INVITE)?.let{
                AppConstants.USER_INVITE = it
            }
//            // An example for using a generic getter
//            var userId: String? = ""
//            try {
//                user = deepLinkObj.deepLinkValue
//                Log.d(LOG_TAG, "The DeepLink will route to: $fruitName")
//            } catch (e: Exception) {
//                Log.d(LOG_TAG, "Custom param fruit_name was not found in DeepLink data")
//                return@DeepLinkListener
//            }
//            goToFruit(fruitName, deepLinkObj)
        })

        val conversionDataListener = object : AppsFlyerConversionListener {
            override fun onConversionDataSuccess(data: MutableMap<String, Any>?) {
                data?.let { cvData ->
                    cvData.map {
                        Log.i(LOG_TAG, "conversion_attribute:  ${it.key} = ${it.value}")
                    }
                }
            }

            override fun onConversionDataFail(error: String?) {
                Log.e(LOG_TAG, "error onAttributionFailure :  $error")
            }

            override fun onAppOpenAttribution(data: MutableMap<String, String>?) {
                data?.map {
                    Log.d(LOG_TAG, "onAppOpen_attribute: ${it.key} = ${it.value}")
                }
            }

            override fun onAttributionFailure(error: String?) {
                Log.e(LOG_TAG, "error onAttributionFailure :  $error")
            }
        }

        appsflyer.init(BuildConfig.KEY_APPS_FLYER, conversionDataListener, this)

        appsflyer.start(this)

        /* Set to true to see the debug logs. Comment out or set to false to stop the function */

        appsflyer.setDebugLog(true)

    }
//
//    private fun goToFruit(fruitName: String, dlData: DeepLink?) {
//        val fruitClassName = fruitName + "Activity"
//        try {
//            val fruitClass = Class.forName(this.packageName + "." + fruitClassName)
//            Log.d(LOG_TAG, "Looking for class $fruitClass")
//            val intent = Intent(applicationContext, fruitClass)
//            if (dlData != null) {
//                // TODO - make DeepLink Parcelable
//                val objToStr = Gson().toJson(dlData)
//                intent.putExtra(DL_ATTRS, objToStr)
//            }
//            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//            startActivity(intent)
//        } catch (e: ClassNotFoundException) {
//            Log.d(LOG_TAG, "Deep linking failed looking for $fruitName")
//            e.printStackTrace()
//        }
//    }
}
