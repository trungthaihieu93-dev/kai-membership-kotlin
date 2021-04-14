package com.kardia.membership.features.fragments.register

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import com.kardia.membership.R
import com.kardia.membership.core.exception.Failure
import com.kardia.membership.core.extension.*
import com.kardia.membership.core.platform.BaseActivity
import com.kardia.membership.core.platform.BaseFragment
import com.kardia.membership.data.entities.UserToken
import com.kardia.membership.domain.entities.auth.RegisterAuthEntity
import com.kardia.membership.domain.entities.captcha.CreateCaptchaEntity
import com.kardia.membership.domain.entities.captcha.VerifyCaptchaEntity
import com.kardia.membership.domain.entities.referral.ReferralEntity
import com.kardia.membership.domain.entities.user.UserInfoEntity
import com.kardia.membership.domain.usecases.auth.PostRegisterAuthUseCase
import com.kardia.membership.domain.usecases.auth.PostResetPasswordConfirmUseCase
import com.kardia.membership.domain.usecases.captcha.PostVerifyCaptchaUseCase
import com.kardia.membership.domain.usecases.referral.PostReferralUseCase
import com.kardia.membership.features.dialog.DialogAlert
import com.kardia.membership.features.dialog.NoInternetDialog
import com.kardia.membership.features.utils.AppConstants
import com.kardia.membership.features.utils.AppLog
import com.kardia.membership.features.utils.CommonUtils
import com.kardia.membership.features.utils.TrackingUtil
import com.kardia.membership.features.viewmodel.AuthViewModel
import com.kardia.membership.features.viewmodel.CaptchaViewModel
import com.kardia.membership.features.viewmodel.ReferralViewModel
import com.kardia.membership.features.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.android.synthetic.main.fragment_register.tietConfirmPassword
import kotlinx.android.synthetic.main.fragment_register.tvMessageConfirmPasswordEmpty
import kotlinx.android.synthetic.main.layout_toolbar.*
import okhttp3.ResponseBody
import java.util.*


class RegisterFragment : BaseFragment() {
    private lateinit var captchaViewModel: CaptchaViewModel
    private lateinit var authViewModel: AuthViewModel
    private lateinit var userViewModel: UserViewModel
    private lateinit var referralViewModel: ReferralViewModel
    private var captchaId = ""
    override fun layoutId() = R.layout.fragment_register

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        captchaViewModel = viewModel(viewModelFactory) {
            observe(createCaptchaEntity, ::onReceiveCreateCaptchaEntity)
            observe(getCaptchaEntity, ::onReceiveGetCaptchaEntity)
            observe(verifyCaptchaEntity, ::onReceiveVerifyCaptchaEntity)
            observe(failureData, ::handleFailure)
        }

        authViewModel = viewModel(viewModelFactory) {
            observe(registerEntity, ::onReceiveRegisterEntity)
            observe(failureData, ::handleFailureRegister)
        }
        userViewModel = viewModel(viewModelFactory) {
            observe(getUserInfoEntity, ::onReceiveUserInfoEntity)
        }
        referralViewModel = viewModel(viewModelFactory) {
            observe(referralEntity, ::onReceiveReferralEntity)
        }

        tracking(TrackingUtil.START)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun initViews() {
        isNotCatch401 = true

        ivBack.visible()
        flPaddingTop.visible()
    }

    override fun initEvents() {
        btCreateAccount.setOnClickListener {
            register()
        }
        tvSignIn.setOnClickListener {
            finish()
        }
        ivBack.setOnClickListener {
            finish()
        }

        ivRefreshCaptcha.setOnClickListener {
            reloadData()
        }
    }

    override fun loadData() {
        showProgress()
        captchaViewModel.createCaptcha()
    }

    override fun reloadData() {
        tietCaptcha.clear()
        loadData()
    }


    private fun handleFailureRegister(failure: Failure?) {
        when (failure) {
            is Failure.NetworkConnection -> {
                if (!(activity as BaseActivity).isShowNoInternet) {
                    (activity as BaseActivity).isShowNoInternet = true
                    activity?.let {
                        NoInternetDialog().apply {
                            onDismiss {
                                (activity as BaseActivity).isShowNoInternet = false
                            }
                        }
                            .onNegative { reloadData() }
                            .onPositive { }.show(requireContext())
                    }
                }
            }
            is Failure.ServerError -> {
                AppLog.e("Duy", "${failure.error} : ${(activity as BaseActivity).isShowError}")
                if (failure.errorCode == 401 && !isNotCatch401) {
                    DialogAlert()
                        .setTitle("This account is already logged into on another device.")
                        .setMessage("You are currently logged in on another device. Please log out of the other device or contact your administrator.")
                        .setCancel(false)
                        .setTitlePositive("OK")
                        .onPositive {
                            mNavigator.showSelectAccountNew(activity)
                        }
                        .show(requireContext())
                    hideProgress()
                } else {
                    if (failure.error.contains("The server is down")) {
                        if (!(activity as BaseActivity).isShowNoInternet) {
                            (activity as BaseActivity).isShowNoInternet = true
                            activity?.let {
//                                NoInternetDialog().apply {
//                                    onDismiss {
//                                        (activity as BaseActivity).isShowNoInternet = false
//                                    }
//                                }
//                                    .onPositive { onReloadData() }.show(requireContext())
                            }
                        }
                    } else if (!(activity as BaseActivity).isShowError) {
                        (activity as BaseActivity).isShowError = true
                        if (failure.error.contains("The server is busy. Please try again.")) {
                            DialogAlert().setTitle("Sorry!")
                                .setMessage("${failure.error}")
                                .setTitlePositive("Retry")
                                .onPositive { reloadData() }
                                .onDismiss { (activity as BaseActivity).isShowError = false }
                                .show(requireContext())
                        } else {
                            CommonUtils.showError(
                                activity,
                                getString(R.string.announcement),
                                "${failure.error}"
                            )
                        }
                    }
                }
            }
        }
        BaseActivity.apiRequestCount = 0
        hideProgress()
        captchaViewModel.createCaptcha()
        ivCaptcha.invisible()
    }

    private fun onReceiveCreateCaptchaEntity(entity: CreateCaptchaEntity?) {
        hideProgress()
        entity?.data?.captcha_id?.let {
            captchaId = it
            showProgress()
            captchaViewModel.getCaptcha(it)
        }
    }


    private fun onReceiveGetCaptchaEntity(entity: ResponseBody?) {
        forceHide()
        ivCaptcha.visible()
        entity?.let {
            val bmp = BitmapFactory.decodeStream(it.byteStream())
            ivCaptcha.setImageBitmap(bmp)
        }
    }

    private fun onReceiveVerifyCaptchaEntity(entity: VerifyCaptchaEntity?) {
        val firstName = "User"
        val lastName = Date().time.toString()
        authViewModel.registerAuth(
            PostRegisterAuthUseCase.Params(
                tietEmail.text.toString().trim(),
                tietPassword.text.toString().trim(),
                tietEmail.text.toString().trim(),
                firstName,
                lastName
            )
        )
//        mNavigator.showCreatePasscode(activity, tietEmail.text.toString().trim())
//        finish()
    }

    private fun onReceiveRegisterEntity(entity: RegisterAuthEntity?) {
        forceHide()
        entity?.data?.let {
            referralViewModel.referral(PostReferralUseCase.Params(AppConstants.USER_INVITE))
            userTokenCache.put(
                UserToken(
                    it.access_token,
                    it.refresh_token,
                    it.expires_in,
                    it.is_first
                )
            )
            mNavigator.showCreatePasscode(activity, tietEmail.text.toString().trim())
            finish()
        }
    }


    private fun register() {
        tvMessageEmailEmpty.gone()
        tvMessagePasswordEmpty.gone()
        tvMessageConfirmPasswordEmpty.gone()
        tvMessageCaptchaEmpty.gone()
        val email = tietEmail.text.toString().trim()
        val password = tietPassword.text.toString().trim()
        val confirmPassword = tietConfirmPassword.text.toString().trim()
        val captcha = tietCaptcha.text.toString().trim()
        if (email.isEmpty()) {
            tvMessageEmailEmpty.visible()
        }
        if (password.isEmpty()) {
            tvMessagePasswordEmpty.visible()
        }
        if (confirmPassword.isEmpty()) {
            tvMessageConfirmPasswordEmpty.visible()
            tvMessageConfirmPasswordEmpty.text = getString(R.string.confirm_password_empty)
        }
        if (captcha.isEmpty()) {
            tvMessageCaptchaEmpty.visible()
        }
        if (email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty() && captcha.isNotEmpty()) {
            if (password == confirmPassword) {
                showProgress()
                captchaViewModel.verifyCaptcha(PostVerifyCaptchaUseCase.Params(captchaId, captcha))
            } else {
                tvMessageConfirmPasswordEmpty.visible()
                tvMessageConfirmPasswordEmpty.text =
                    getString(R.string.confirm_password_not_correct)
            }
        }
    }

    private fun onReceiveUserInfoEntity(entity: UserInfoEntity?) {
        entity?.data?.let {
            tracking(TrackingUtil.COMPLETE_REGISTRATION, it.user_info)
        }
    }

    private fun onReceiveReferralEntity(entity: ReferralEntity?) {
    }

}