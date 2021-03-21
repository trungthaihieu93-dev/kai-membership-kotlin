package com.kardia.membership.features.fragments.create_passcode

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.kardia.membership.R
import com.kardia.membership.core.extension.observe
import com.kardia.membership.core.extension.viewModel
import com.kardia.membership.core.platform.BaseFragment
import com.kardia.membership.domain.entities.passcode.RegisterPasscodeEntity
import com.kardia.membership.domain.usecases.passcode.PostRegisterPasscodeUseCase
import com.kardia.membership.features.utils.AppConstants
import com.kardia.membership.features.viewmodel.PasscodeViewModel
import kotlinx.android.synthetic.main.fragment_create_passcode.*
import kotlinx.android.synthetic.main.fragment_create_passcode.ovPasscode

class CreatePasscodeFragment : BaseFragment() {

    private var email: String? = null
    private var otp: String? = null

    companion object {
        const val EMAIL = "email"
    }

    override fun layoutId() = R.layout.fragment_create_passcode

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        email = arguments?.getString(EMAIL)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun initViews() {

    }

    override fun initEvents() {
        btSetPasscode.setOnClickListener {
            otp?.let { otp ->
                mNavigator.showConfirmPasscode(activity, email, otp, true)
            }
        }

        ovPasscode.setOtpCompletionListener {
            mNavigator.showConfirmPasscode(activity, email, it, true)
        }
        ovPasscode.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                otp = if (ovPasscode.text.toString().trim().length < 4) {
                    null
                } else {
                    ovPasscode.text.toString().trim()
                }
            }
        })

//        tvShowPasscode.setOnClickListener {
//            ovPasscode.item
//        }
    }

    override fun loadData() {
    }

    override fun reloadData() {

    }
}