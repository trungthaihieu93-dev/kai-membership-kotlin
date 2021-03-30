package com.kardia.membership

import android.annotation.SuppressLint
import android.app.Application
import android.provider.Settings
import com.kardia.membership.core.di.ApplicationComponent
import com.kardia.membership.core.di.ApplicationModule
import com.kardia.membership.core.di.DaggerApplicationComponent
import com.kardia.membership.features.utils.AppConstants

class AndroidApplication : Application() {

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

        val deviceId: String = Settings.Secure.getString(
            contentResolver,
            Settings.Secure.ANDROID_ID
        )
        AppConstants.DEVICE_ID = "e3a0a9e5a5b5d767"
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

}
