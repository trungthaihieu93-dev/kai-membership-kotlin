package com.kardia.membership.features.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.kardia.membership.core.platform.BaseActivity
import com.kardia.membership.core.platform.BaseFragment
import com.kardia.membership.features.fragments.check_mail.CheckMailFragment

class CheckMailActivity : BaseActivity() {
    companion object {
        fun callingIntent(context: Context) =
            Intent(context, CheckMailActivity::class.java)
    }

    override fun fragment(): BaseFragment? {
        return CheckMailFragment().apply {
            arguments = Bundle().apply {
                putString(
                    CheckMailFragment.EMAIL, intent.getStringExtra(
                        CheckMailFragment.EMAIL
                    )
                )
                putBoolean(
                    CheckMailFragment.FROM_MISSION, true
                )
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        changeFullScreenMode()
    }
}