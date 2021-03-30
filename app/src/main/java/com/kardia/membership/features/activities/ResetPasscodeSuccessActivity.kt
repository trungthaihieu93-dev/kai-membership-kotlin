package com.kardia.membership.features.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.kardia.membership.core.platform.BaseActivity
import com.kardia.membership.core.platform.BaseFragment
import com.kardia.membership.features.fragments.reset_passcode_success.ResetPasscodeSuccessFragment

class ResetPasscodeSuccessActivity  : BaseActivity() {
    companion object {
        fun callingIntent(context: Context) = Intent(context, ResetPasscodeSuccessActivity::class.java)
    }

    override fun fragment(): BaseFragment? {
        return ResetPasscodeSuccessFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        changeFullScreenMode()
    }
}