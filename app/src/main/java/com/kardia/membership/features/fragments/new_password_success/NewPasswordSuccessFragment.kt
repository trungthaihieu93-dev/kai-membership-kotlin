package com.kardia.membership.features.fragments.new_password_success

import android.os.Bundle
import android.view.View
import com.kardia.membership.R
import com.kardia.membership.core.platform.BaseFragment
import kotlinx.android.synthetic.main.fragment_new_password_success.*

class NewPasswordSuccessFragment : BaseFragment() {
    override fun layoutId() = R.layout.fragment_new_password_success

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
            finish()
        }
    }

    override fun loadData() {
    }

    override fun reloadData() {

    }

}