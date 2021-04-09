package com.kardia.membership.features.fragments.confirm_passcode

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.kardia.membership.R
import com.kardia.membership.core.extension.*
import com.kardia.membership.core.platform.BaseFragment
import com.kardia.membership.domain.entities.passcode.RegisterPasscodeEntity
import com.kardia.membership.domain.entities.passcode.ResetPasscodeEntity
import com.kardia.membership.domain.usecases.passcode.PostRegisterPasscodeUseCase
import com.kardia.membership.domain.usecases.passcode.PostResetPasscodeUseCase
import com.kardia.membership.features.utils.AppConstants
import com.kardia.membership.features.viewmodel.PasscodeViewModel
import kotlinx.android.synthetic.main.fragment_confirm_passcode.*
import kotlinx.android.synthetic.main.fragment_confirm_passcode.ovPasscode
import kotlinx.android.synthetic.main.layout_toolbar.*

class ConfirmPasscodeFragment : BaseFragment() {
    private lateinit var passcodeViewModel: PasscodeViewModel

    private var email: String? = null
    private var passcodeConfirm: String? = null
    private var passcode: String? = null
    private var isFromRegister: Boolean = false
    private var token: String? = null

    companion object {
        const val EMAIL = "email"
        const val PASSCODE = "passcode"
        const val IS_FROM_REGISTER = "isFromRegister"
        const val TOKEN = "token"
    }

    override fun layoutId() = R.layout.fragment_confirm_passcode

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        passcodeViewModel = viewModel(viewModelFactory) {
            observe(registerEntity, ::onReceiveRegisterPasscodeEntity)
            observe(resetEntity, ::onReceiveResetPasscodeEntity)
            observe(failureData, ::handleFailure)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        email = arguments?.getString(EMAIL)
        passcode = arguments?.getString(PASSCODE)
        arguments?.getBoolean(IS_FROM_REGISTER, false)?.let {
            isFromRegister = it
        }
        token = arguments?.getString(TOKEN)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun initViews() {
        isNotCatch401 = true
        ivBack.visible()
    }

    override fun initEvents() {
        btConfirm.setOnClickListener {
            passcodeConfirm?.let {
                actionPasscode(it)
            }
        }
        ivBack.setOnClickListener {
            close()
        }

        ovPasscode.setOtpCompletionListener {
            actionPasscode(it)
        }
        ovPasscode.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                passcodeConfirm = if (ovPasscode.text.toString().trim().length < 4) {
                    null
                } else {
                    ovPasscode.text.toString().trim()
                }
            }
        })
    }

    private fun actionPasscode(passcodeConfirm: String?) {
        tvMessageConfirmPasscodeNotCorrect.gone()
        if (passcode == passcodeConfirm) {
            showProgress()
            if (isFromRegister) {
                passcodeViewModel.registerPasscode(
                    PostRegisterPasscodeUseCase.Params(
                        AppConstants.DEVICE_ID,
                        passcode,
                        userTokenCache.get()?.refreshToken,
                        email,
                        AppConstants.DEVICE_OS
                    )
                )
            } else {
                passcodeViewModel.resetPasscode(
                    PostResetPasscodeUseCase.Params(
                        AppConstants.DEVICE_ID,
                        passcode,
                        token,
                        email
                    )
                )
            }
        } else {
            tvMessageConfirmPasscodeNotCorrect.visible()
        }
    }

    override fun loadData() {
    }

    override fun reloadData() {

    }

    private fun onReceiveRegisterPasscodeEntity(entity: RegisterPasscodeEntity?) {
        hideProgress()
        mNavigator.showRegisterSuccess(activity)
    }

    private fun onReceiveResetPasscodeEntity(entity: ResetPasscodeEntity?) {
        hideProgress()
        mNavigator.showResetPasscodeSuccess(activity)
    }
}