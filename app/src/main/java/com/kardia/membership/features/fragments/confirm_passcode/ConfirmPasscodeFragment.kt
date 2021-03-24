package com.kardia.membership.features.fragments.confirm_passcode

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.kardia.membership.R
import com.kardia.membership.core.extension.*
import com.kardia.membership.core.platform.BaseFragment
import com.kardia.membership.domain.entities.passcode.RegisterPasscodeEntity
import com.kardia.membership.domain.usecases.passcode.PostRegisterPasscodeUseCase
import com.kardia.membership.features.fragments.create_passcode.CreatePasscodeFragment
import com.kardia.membership.features.utils.AppConstants
import com.kardia.membership.features.viewmodel.PasscodeViewModel
import kotlinx.android.synthetic.main.fragment_confirm_passcode.*
import kotlinx.android.synthetic.main.fragment_confirm_passcode.ovPasscode
import kotlinx.android.synthetic.main.fragment_create_passcode.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class ConfirmPasscodeFragment : BaseFragment() {
    private lateinit var passcodeViewModel: PasscodeViewModel

    private var email: String? = null
    private var otpConfirm: String? = null
    private var otp: String? = null
    private var isFromRegister: Boolean = false

    companion object {
        const val EMAIL = "email"
        const val OTP = "otp"
        const val IS_FROM_REGISTER = "isFromRegister"
    }

    override fun layoutId() = R.layout.fragment_confirm_passcode

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        passcodeViewModel = viewModel(viewModelFactory) {
            observe(registerEntity, ::onReceiveRegisterPasscodeEntity)
            observe(failureData, ::handleFailure)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        email = arguments?.getString(EMAIL)
        otp = arguments?.getString(OTP)
        arguments?.getBoolean(IS_FROM_REGISTER, false)?.let {
            isFromRegister = it
        }

        super.onViewCreated(view, savedInstanceState)
    }

    override fun initViews() {
        ivBack.visible()
    }

    override fun initEvents() {
        btConfirm.setOnClickListener {
            otpConfirm?.let {
                registerPasscode(it)
            }
        }
        ivBack.setOnClickListener {
            close()
        }

        ovPasscode.setOtpCompletionListener {
            registerPasscode(it)
        }
        ovPasscode.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                otpConfirm = if (ovPasscode.text.toString().trim().length < 4) {
                    null
                } else {
                    ovPasscode.text.toString().trim()
                }
            }
        })
    }

    private fun registerPasscode(otpConfirm: String?) {
        tvMessageConfirmPasscodeNotCorrect.gone()
        if (otp == otpConfirm) {
            if (isFromRegister) {
                showProgress()
                passcodeViewModel.registerPasscode(
                    PostRegisterPasscodeUseCase.Params(
                        AppConstants.DEVICE_ID_TEST,
                        otp,
                        userTokenCache.get()?.refreshToken,
                        email,
                        AppConstants.DEVICE_OS
                    )
                )
            }
        }
        else {
            tvMessageConfirmPasscodeNotCorrect.visible()
        }
    }

    override fun loadData() {
    }

    override fun reloadData() {

    }

    private fun onReceiveRegisterPasscodeEntity(entity: RegisterPasscodeEntity?) {
        hideProgress()
        entity?.data?.let { pd ->
            mNavigator.showLoginNew(activity)
        }
    }

}