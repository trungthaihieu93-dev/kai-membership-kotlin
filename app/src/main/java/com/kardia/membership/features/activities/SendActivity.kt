package com.kardia.membership.features.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.kardia.membership.core.platform.BaseActivity
import com.kardia.membership.core.platform.BaseFragment
import com.kardia.membership.features.fragments.send.SendFragment

class SendActivity : BaseActivity() {
    companion object {
        fun callingIntent(context: Context) = Intent(context, SendActivity::class.java)
    }

    override fun fragment(): BaseFragment? {
        return SendFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        changeColorStatusBar()
    }
}