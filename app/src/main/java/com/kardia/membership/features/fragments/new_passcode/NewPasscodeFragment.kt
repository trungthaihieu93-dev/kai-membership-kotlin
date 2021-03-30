package com.kardia.membership.features.fragments.new_passcode

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.kardia.membership.R
import com.kardia.membership.core.platform.BaseFragment
import kotlinx.android.synthetic.main.fragment_create_passcode.*
import kotlinx.android.synthetic.main.fragment_new_passcode.*
import kotlinx.android.synthetic.main.fragment_new_passcode.btSetPasscode
import kotlinx.android.synthetic.main.fragment_new_passcode.ovPasscode

class NewPasscodeFragment : BaseFragment() {

    private var token: String? = null
    private var passcode: String? = null
    private var email: String? = null

    companion object {
        const val TOKEN = "token"
        const val EMAIL = "email"
    }

    override fun layoutId() = R.layout.fragment_new_passcode

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        token = arguments?.getString(TOKEN)
        email = arguments?.getString(EMAIL)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun initViews() {

    }

    override fun initEvents() {
        btSetPasscode.setOnClickListener {
            passcode?.let { passcode ->
                mNavigator.showConfirmPasscodeReset(activity, token, passcode,email)
            }
        }

        ovPasscode.setOtpCompletionListener {
            mNavigator.showConfirmPasscodeReset(activity, token, it,email)
        }
        ovPasscode.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                passcode = if (ovPasscode.text.toString().trim().length < 4) {
                    null
                } else {
                    ovPasscode.text.toString().trim()
                }
            }
        })
    }

    override fun loadData() {
    }

    override fun reloadData() {

    }

}