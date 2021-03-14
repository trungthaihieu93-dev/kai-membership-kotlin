package com.kardia.membership.features.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.kardia.membership.core.platform.BaseActivity
import com.kardia.membership.core.platform.BaseFragment
import com.kardia.membership.features.fragments.top_up.TopUpFragment

class TopUpActivity: BaseActivity() {
    companion object {
        fun callingIntent(context: Context) = Intent(context, TopUpActivity::class.java)
    }

    override fun fragment(): BaseFragment? {
        return TopUpFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        changeColorStatusBar()
    }
}