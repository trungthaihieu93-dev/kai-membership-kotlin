package com.kardia.membership.features.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.kardia.membership.core.platform.BaseActivity
import com.kardia.membership.core.platform.BaseFragment
import com.kardia.membership.features.fragments.new_password_success.NewPasswordSuccessFragment
import com.kardia.membership.features.fragments.register.RegisterFragment

class NewPasswordSuccessActivity : BaseActivity() {
    companion object {
        fun callingIntent(context: Context) = Intent(context, NewPasswordSuccessActivity::class.java)
    }

    override fun fragment(): BaseFragment? {
        return NewPasswordSuccessFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        changeFullScreenMode()
    }
}