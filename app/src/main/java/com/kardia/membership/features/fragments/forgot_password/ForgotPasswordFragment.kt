package com.kardia.membership.features.fragments.forgot_password

import android.os.Bundle
import android.view.View
import com.kardia.membership.R
import com.kardia.membership.core.platform.BaseFragment
import kotlinx.android.synthetic.main.fragment_forgot_password.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class ForgotPasswordFragment : BaseFragment() {
    override fun layoutId() = R.layout.fragment_forgot_password

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
        btSendInstructions.setOnClickListener {
            mNavigator.showNewPassword(activity)
        }
        ivBack.setOnClickListener {
            finish()
        }
    }

    override fun loadData() {
    }

    override fun reloadData() {

    }

}