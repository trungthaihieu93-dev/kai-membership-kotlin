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
        return SelectAccountFragment().apply {
            arguments = Bundle().apply {
                putParcelable(
                    SelectAccountFragment.PASSCODE_DEVICE,intent.getParcelableExtra(
                        SelectAccountFragment.PASSCODE_DEVICE))
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        changeColorStatusBar()
    }
}