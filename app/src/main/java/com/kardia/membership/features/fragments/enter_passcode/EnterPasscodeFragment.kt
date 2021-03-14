package com.kardia.membership.features.fragments.enter_passcode

import android.content.res.ColorStateList
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.ContextMenu
import android.view.View
import androidx.core.content.ContextCompat
import com.airbnb.paris.Paris
import com.google.android.material.button.MaterialButton
import com.kardia.membership.R
import com.kardia.membership.core.extension.*
import com.kardia.membership.core.platform.BaseFragment
import com.kardia.membership.data.entities.UserToken
import com.kardia.membership.domain.entities.device.PasscodeDeviceEntity
import com.kardia.membership.domain.entities.passcode.CheckPasscodeEntity
import com.kardia.membership.domain.entities.passcode.LoginPasscodeEntity
import com.kardia.membership.domain.usecases.passcode.PostCheckPasscodeUseCase
import com.kardia.membership.domain.usecases.passcode.PostLoginPasscodeUseCase
import com.kardia.membership.features.utils.AppConstants
import com.kardia.membership.features.viewmodel.DeviceViewModel
import com.kardia.membership.features.viewmodel.PasscodeViewModel
import kotlinx.android.synthetic.main.fragment_enter_passcode.*
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class EnterPasscodeFragment : BaseFragment() {
    private lateinit var passcodeViewModel: PasscodeViewModel

    private var email: String? = null
    private var otp: String? = null
    private var fromChangePassword: Boolean = false

    companion object {
        const val EMAIL = "email"
        const val FROM_CHANGE_PASSWORD = "fromChangePassword"
    }

    override fun layoutId() = R.layout.fragment_enter_passcode

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        passcodeViewModel = viewModel(viewModelFactory) {
            observe(loginEntity, ::onReceiveLoginPasscodeEntity)
            observe(checkEntity, ::onReceiveCheckPasscodeEntity)
            observe(failureData, ::handleFailure)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        email = arguments?.getString(EMAIL)
        arguments?.getBoolean(FROM_CHANGE_PASSWORD, false)?.let {
            fromChangePassword = it
        }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun initViews() {
        ivBack.visible()

        if (!fromChangePassword) {
            flPaddingTop.gone()
        } else {
            flPaddingTop.visible()
        }
    }

    override fun initEvents() {
        tvResetPasscode.setOnClickListener {
            mNavigator.showResetPasscode(activity)
        }

        ivBack.setOnClickListener {
            close()
        }

        btContinue.setOnClickListener {
            otp?.let { otp ->
                showProgress()
                if (!fromChangePassword) {
                    passcodeViewModel.loginPasscode(
                        PostLoginPasscodeUseCase.Params(
                            otp,
                            email,
                            AppConstants.DEVICE_ID_TEST,
                            AppConstants.DEVICE_OS
                        )
                    )
                } else {
                    passcodeViewModel.checkPasscode(
                        PostCheckPasscodeUseCase.Params(
                            otp,
                            AppConstants.DEVICE_ID_TEST
                        )
                    )
                }
            }
        }

        ovPasscode.setOtpCompletionListener {
            showProgress()
            if (!fromChangePassword) {
                passcodeViewModel.loginPasscode(
                    PostLoginPasscodeUseCase.Params(
                        it,
                        email,
                        AppConstants.DEVICE_ID_TEST,
                        AppConstants.DEVICE_OS
                    )
                )
            } else {
                passcodeViewModel.checkPasscode(
                    PostCheckPasscodeUseCase.Params(
                        it,
                        AppConstants.DEVICE_ID_TEST
                    )
                )
            }
        }
        ovPasscode.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (ovPasscode.text.toString().trim().length < 4) {
                    otp = null
                }
            }
        })
    }

    override fun loadData() {

    }

    override fun reloadData() {

    }

    private fun onReceiveLoginPasscodeEntity(entity: LoginPasscodeEntity?) {
        hideProgress()
        ovPasscode.setText("")
        entity?.data?.let {
            userInfoCache.clear()
            userTokenCache.put(UserToken(it.access_token, it.refresh_token, it.expires_in))
            mNavigator.showMain(activity)
        }
        finish()
    }

    private fun onReceiveCheckPasscodeEntity(entity: CheckPasscodeEntity?) {
        hideProgress()
        ovPasscode.setText("")
        mNavigator.showNewPassword(activity, true)
    }
}