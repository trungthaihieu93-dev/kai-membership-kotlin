package com.kardia.membership.features.fragments.reset_passcode_success

import android.os.Bundle
import android.view.View
import com.kardia.membership.R
import com.kardia.membership.core.platform.BaseFragment
import kotlinx.android.synthetic.main.fragment_reset_passcode_success.*

class ResetPasscodeSuccessFragment : BaseFragment() {
    override fun layoutId() = R.layout.fragment_reset_passcode_success

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun initViews() {

    }

    override fun initEvents() {
        btOKIGotIt.setOnClickListener {
            mNavigator.showLoginNew(activity)
        }
    }

    override fun loadData() {
    }

    override fun reloadData() {

    }

}