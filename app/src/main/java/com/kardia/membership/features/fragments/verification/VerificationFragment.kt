package com.kardia.membership.features.fragments.verification

import android.os.Bundle
import android.view.View
import com.kardia.membership.R
import com.kardia.membership.core.extension.close
import com.kardia.membership.core.extension.gone
import com.kardia.membership.core.extension.visible
import com.kardia.membership.core.platform.BaseFragment
import com.kardia.membership.features.fragments.new_passcode.NewPasscodeFragment
import kotlinx.android.synthetic.main.fragment_verification.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class VerificationFragment : BaseFragment() {
    private var email: String? = null

    companion object {
        const val EMAIL = "email"
    }


    override fun layoutId() = R.layout.fragment_verification

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        email = arguments?.getString(EMAIL)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun initViews() {
        ivBack.visible()
    }

    override fun initEvents() {
        ivBack.setOnClickListener {
            close()
        }

        btSetNewPasscode.setOnClickListener {
            tvMessageTokenEmpty.gone()
            val token = tietToken.text.toString().trim()
            if (token.isNotEmpty()) {
                mNavigator.showNewPasscode(activity, token, email)
            } else {
                tvMessageTokenEmpty.visible()
            }
        }
    }

    override fun loadData() {
    }

    override fun reloadData() {

    }

}