package com.kardia.membership.features.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.kardia.membership.core.platform.BaseActivity
import com.kardia.membership.core.platform.BaseFragment
import com.kardia.membership.features.fragments.introduce.IntroduceManagementFragment

class IntroduceManagementActivity  : BaseActivity() {
    companion object {
        fun callingIntent(context: Context) = Intent(context, IntroduceManagementActivity::class.java)
    }

    override fun fragment(): BaseFragment? = IntroduceManagementFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        changeFullScreenMode()
    }
}