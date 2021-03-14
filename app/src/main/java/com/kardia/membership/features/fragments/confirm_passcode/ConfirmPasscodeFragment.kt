package com.kardia.membership.features.fragments.confirm_passcode

import android.os.Bundle
import android.view.View
import com.kardia.membership.R
import com.kardia.membership.core.extension.close
import com.kardia.membership.core.extension.visible
import com.kardia.membership.core.platform.BaseFragment
import kotlinx.android.synthetic.main.fragment_confirm_passcode.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class ConfirmPasscodeFragment: BaseFragment() {
    override fun layoutId() = R.layout.fragment_confirm_passcode

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun initViews() {
        ivBack.visible()
    }

    override fun initEvents() {
        btConfirm.setOnClickListener {
            mNavigator.showRegisterSuccess(activity)
        }
        ivBack.setOnClickListener {
            close()
        }
    }

    override fun loadData() {
    }

    override fun reloadData() {

    }

}