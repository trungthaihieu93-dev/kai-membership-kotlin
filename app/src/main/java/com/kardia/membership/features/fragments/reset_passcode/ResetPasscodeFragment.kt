package com.kardia.membership.features.fragments.reset_passcode

import android.os.Bundle
import android.view.View
import com.kardia.membership.R
import com.kardia.membership.core.extension.*
import com.kardia.membership.core.platform.BaseFragment
import com.kardia.membership.domain.entities.passcode.CheckPasscodeEntity
import com.kardia.membership.domain.entities.passcode.ForgotPasscodeEntity
import com.kardia.membership.domain.usecases.auth.PostResetPasswordUseCase
import com.kardia.membership.domain.usecases.passcode.PostForgotPasscodeUseCase
import com.kardia.membership.features.fragments.enter_passcode.EnterPasscodeFragment
import com.kardia.membership.features.utils.AppConstants
import com.kardia.membership.features.viewmodel.PasscodeViewModel
import kotlinx.android.synthetic.main.fragment_reset_passcode.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class ResetPasscodeFragment : BaseFragment() {
    private lateinit var passcodeViewModel: PasscodeViewModel
    private var email: String? = null

    companion object {
        const val EMAIL = "email"
    }

    override fun layoutId() = R.layout.fragment_reset_passcode

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        passcodeViewModel = viewModel(viewModelFactory) {
            observe(forgotEntity, ::onReceiveForgotPasscodeEntity)
            observe(failureData, ::handleFailure)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        email = arguments?.getString(EMAIL)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun initViews() {
        ivBack.visible()

        email?.let{
            tietEmail.setText(it)
        }
    }

    override fun initEvents() {
        btSendInstructions.setOnClickListener {
            tvMessageEmailEmpty.gone()
            val email = tietEmail.text.toString().trim()
            if (email.isEmpty()) {
                tvMessageEmailEmpty.visible()
            }
            if (email.isNotEmpty()) {
                showProgress()
                passcodeViewModel.forgotPasscode(
                    PostForgotPasscodeUseCase.Params(
                        email,
                        AppConstants.DEVICE_ID_TEST
                    )
                )
//                mNavigator.showCheckMail(activity,tietEmail.text.toString().trim())
            }
        }
        ivBack.setOnClickListener {
            close()
        }
    }

    override fun loadData() {
    }

    override fun reloadData() {

    }

    private fun onReceiveForgotPasscodeEntity(entity: ForgotPasscodeEntity?) {
        forceHide()
        mNavigator.showCheckMail(activity,tietEmail.text.toString().trim())
    }

}