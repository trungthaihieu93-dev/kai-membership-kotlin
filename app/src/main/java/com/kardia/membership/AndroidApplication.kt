package com.kardia.membership

import android.app.Application
import com.kardia.membership.core.di.ApplicationComponent
import com.kardia.membership.core.di.ApplicationModule
import com.kardia.membership.core.di.DaggerApplicationComponent

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

    override fun onCreate() {
        super.onCreate()
        this.injectMembers()
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
