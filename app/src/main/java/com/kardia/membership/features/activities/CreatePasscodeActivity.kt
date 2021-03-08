package com.kardia.membership.features.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.kardia.membership.R
import com.kardia.membership.core.platform.BaseActivity
import com.kardia.membership.core.platform.BaseFragment
import com.kardia.membership.features.fragments.create_passcode.CreatePasscodeFragment
import com.kardia.membership.features.fragments.login.LoginFragment

class CreatePasscodeActivity : BaseActivity() {
    companion object {
        fun callingIntent(context: Context) = Intent(context, CreatePasscodeActivity::class.java)
    }

    override fun fragment(): BaseFragment? {
        return CreatePasscodeFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        changeColorStatusBar()
    }
}