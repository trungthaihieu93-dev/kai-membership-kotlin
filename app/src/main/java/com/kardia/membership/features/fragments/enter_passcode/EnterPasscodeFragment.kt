package com.kardia.membership.features.fragments.enter_passcode

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.SpannableStringBuilder
import android.text.TextPaint
import android.text.TextWatcher
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import androidx.core.text.color
import androidx.core.text.set
import com.kardia.membership.R
import com.kardia.membership.core.extension.*
import com.kardia.membership.core.platform.BaseFragment
import com.kardia.membership.data.entities.UserToken
import com.kardia.membership.domain.entities.auth.ResetPasswordEntity
import com.kardia.membership.domain.entities.passcode.CheckPasscodeEntity
import com.kardia.membership.domain.entities.passcode.LoginPasscodeEntity
import com.kardia.membership.domain.usecases.auth.PostResetPasswordUseCase
import com.kardia.membership.domain.usecases.passcode.PostCheckPasscodeUseCase
import com.kardia.membership.domain.usecases.passcode.PostLoginPasscodeUseCase
import com.kardia.membership.features.utils.AppConstants
import com.kardia.membership.features.viewmodel.AuthViewModel
import com.kardia.membership.features.viewmodel.PasscodeViewModel
import kotlinx.android.synthetic.main.fragment_enter_passcode.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class EnterPasscodeFragment : BaseFragment() {
    private lateinit var passcodeViewModel: PasscodeViewModel
    private lateinit var authViewModel: AuthViewModel

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

        authViewModel = viewModel(viewModelFactory) {
            observe(resetPasswordEntity, ::onReceiveResetPasswordEntity)
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

        val myCustomizedString = SpannableStringBuilder()
            .append(getString(R.string.content_forgot_code))
            .append(" ")
            .color(getColor(R.color.color_94A2B2)) {
                append(getString(R.string.click_here))
            }
        myCustomizedString[getString(R.string.content_forgot_code).length + 1 until getString(
            R.string.content_forgot_code
        ).length + 1 + getString(R.string.click_here).length + 1] =
            object : ClickableSpan() {
                override fun onClick(view: View) {
                    mNavigator.showResetPasscode(activity, email)
                }

                override fun updateDrawState(ds: TextPaint) { // override updateDrawState
                    ds.isUnderlineText = false // set to false to remove underline
                }
            }
        tvResetPasscode.highlightColor = Color.TRANSPARENT;
        tvResetPasscode.movementMethod = LinkMovementMethod()
        tvResetPasscode.text = myCustomizedString
    }

    override fun initEvents() {

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
                            AppConstants.DEVICE_ID,
                            AppConstants.DEVICE_OS
                        )
                    )
                } else {
                    passcodeViewModel.checkPasscode(
                        PostCheckPasscodeUseCase.Params(
                            otp,
                            AppConstants.DEVICE_ID
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
                        AppConstants.DEVICE_ID,
                        AppConstants.DEVICE_OS
                    )
                )
            } else {
                passcodeViewModel.checkPasscode(
                    PostCheckPasscodeUseCase.Params(
                        it,
                        AppConstants.DEVICE_ID
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
            userTokenCache.put(UserToken(it.access_token, it.refresh_token, it.expires_in,it.is_first))
            mNavigator.showMain(activity)
        }
        finish()
    }

    private fun onReceiveCheckPasscodeEntity(entity: CheckPasscodeEntity?) {
        hideProgress()
        email?.let {
            showProgress()
            authViewModel.resetPassword(PostResetPasswordUseCase.Params(it))
        }
    }

    private fun onReceiveResetPasswordEntity(entity: ResetPasswordEntity?) {
        ovPasscode.setText("")
        forceHide()
        mNavigator.showNewPassword(activity, true)
    }
}