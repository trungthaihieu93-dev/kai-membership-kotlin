package com.kardia.membership.features.fragments.login

import android.os.Bundle
import android.view.View
import com.kardia.membership.R
import com.kardia.membership.core.platform.BaseFragment
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : BaseFragment() {
    override fun layoutId() = R.layout.fragment_login

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
        tvCreateAccount.setOnClickListener {
            mNavigator.showRegister(activity)
        }

        tvForgotPassword.setOnClickListener {
            mNavigator.showForgotPassword(activity)
        }
    }

    override fun loadData() {
    }

    override fun reloadData() {

    }

}