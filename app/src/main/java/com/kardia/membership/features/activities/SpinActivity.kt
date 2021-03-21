package com.kardia.membership.features.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.kardia.membership.core.platform.BaseActivity
import com.kardia.membership.core.platform.BaseFragment
import com.kardia.membership.features.fragments.spin.SpinFragment

class SpinActivity : BaseActivity() {
    override fun fragment(): BaseFragment {
        return SpinFragment()
    }

    companion object {
        fun callingIntent(context: Context) = Intent(context, SpinActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        changeFullScreenMode()
    }
}