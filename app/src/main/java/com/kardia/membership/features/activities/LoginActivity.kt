package com.kardia.membership.features.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import com.kardia.membership.core.platform.BaseActivity
import com.kardia.membership.core.platform.BaseFragment
import com.kardia.membership.features.fragments.login.LoginFragment

class LoginActivity : BaseActivity() {
    companion object {
        fun callingIntent(context: Context) = Intent(context, LoginActivity::class.java)
    }

    override fun fragment(): BaseFragment? {
        return LoginFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        changeFullScreenMode()
    }
}