package com.kardia.membership.features.fragments.check_mail

import android.os.Bundle
import android.view.View
import com.kardia.membership.R
import com.kardia.membership.core.platform.BaseFragment
import kotlinx.android.synthetic.main.fragment_check_mail.*

class CheckMailFragment : BaseFragment() {
    override fun layoutId() = R.layout.fragment_check_mail

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
        btOpenEmailApp.setOnClickListener {
            finish()
            mNavigator.showLogin(activity)
        }
    }

    override fun loadData() {
    }

    override fun reloadData() {

    }

}