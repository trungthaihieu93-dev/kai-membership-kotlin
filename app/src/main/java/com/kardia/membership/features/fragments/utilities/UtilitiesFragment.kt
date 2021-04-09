package com.kardia.membership.features.fragments.utilities

import android.os.Bundle
import com.kardia.membership.R
import com.kardia.membership.core.platform.BaseFragment
import kotlinx.android.synthetic.main.fragment_utilities.*

class UtilitiesFragment : BaseFragment() {
    override fun layoutId(): Int {
        return R.layout.fragment_utilities
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
    }

    override fun initViews() {
    }

    override fun initEvents() {
        mcvMobileTopUp.setOnClickListener {
            if (isUserLogin) {
                mNavigator.showTopUp(activity)
            } else {
                showLogin(activity)
            }
        }

        mcvKAIStarter.setOnClickListener {
            mNavigator.showKAIStarter(activity)
        }
    }

    override fun loadData() {
    }

    override fun reloadData() {
    }
}