package com.kardia.membership.features.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.kardia.membership.R
import com.kardia.membership.core.platform.BaseActivity
import com.kardia.membership.core.platform.BaseFragment
import com.kardia.membership.features.fragments.select_account.SelectAccountFragment

class SelectAccountActivity : BaseActivity() {
    companion object {
        fun callingIntent(context: Context) = Intent(context, SelectAccountActivity::class.java)
    }

    override fun fragment(): BaseFragment? {
        return SelectAccountFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        changeColorStatusBar(R.color.color_background_app)
    }
}