package com.kardia.membership.features.fragments.register

import android.os.Bundle
import android.view.View
import com.kardia.membership.R
import com.kardia.membership.core.platform.BaseFragment
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class RegisterFragment: BaseFragment() {
    override fun layoutId() = R.layout.fragment_register

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
        btCreateAccount.setOnClickListener {
            finish()
            mNavigator.showCreatePasscode(activity)
        }
        tvSignIn.setOnClickListener {
            finish()
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