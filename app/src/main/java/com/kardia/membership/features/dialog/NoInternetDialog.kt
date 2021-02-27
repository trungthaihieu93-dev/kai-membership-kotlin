package com.kardia.membership.features.dialog

import android.os.Bundle
import com.kardia.membership.R
import com.kardia.membership.core.navigation.Navigator
import javax.inject.Inject

class NoInternetDialog : DialogAlert() {
    @Inject
    lateinit var navigator: Navigator

    var isRetry = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)

        this.setTitle(getString(R.string.announcement))
        this.setMessage(getString(R.string.failure_network_connection))
        this.setCancel(false)
        this.setTitleNegative(getString(R.string.btn_retry))
        if (isRetry) {
            this.setTitlePositive(getString(R.string.btn_ok))
        } else {
            this.setTitlePositive("Setting")
        }
    }
}
