package com.kardia.membership.features.fragments.new_password

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.kardia.membership.R
import com.kardia.membership.core.extension.*
import com.kardia.membership.core.platform.BaseFragment
import com.kardia.membership.domain.entities.auth.ResetPasswordConfirmEntity
import com.kardia.membership.domain.entities.auth.ResetPasswordEntity
import com.kardia.membership.domain.entities.user.ChangePasswordEntity
import com.kardia.membership.domain.usecases.auth.PostResetPasswordConfirmUseCase
import com.kardia.membership.domain.usecases.user.PostChangePasswordUseCase
import com.kardia.membership.features.fragments.new_passcode.ChangePasswordSuccessBottomSheet
import com.kardia.membership.features.viewmodel.AuthViewModel
import com.kardia.membership.features.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_new_password.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class NewPasswordFragment : BaseFragment() {
    private lateinit var userViewModel: UserViewModel
    private lateinit var authViewModel: AuthViewModel

    private var fromChangePassword: Boolean = false

    companion object {
        const val FROM_CHANGE_PASSWORD = "fromChangePassword"
    }

    override fun layoutId() = R.layout.fragment_new_password

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        userViewModel = viewModel(viewModelFactory) {
            observe(changePasswordEntity, ::onReceiveChangePasswordEntity)
            observe(failureData, ::handleFailure)
        }
        authViewModel = viewModel(viewModelFactory) {
            observe(resetPasswordConfirmEntity, ::onReceiveResetPasswordConfirmEntity)
            observe(failureData, ::handleFailure)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.getBoolean(FROM_CHANGE_PASSWORD, false)?.let {
            fromChangePassword = it
        }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun initViews() {
        isNotCatch401 = true
        ivBack.visible()
        flPaddingTop.visible()
//        llToken.gone()
//        llOldPassword.gone()
//        if (!fromChangePassword) {
//            llToken.visible()
//        } else {
//            llOldPassword.visible()
//        }
    }

    override fun initEvents() {
        ivBack.setOnClickListener {
            close()
        }

        btSetNewPassword.setOnClickListener {
//            if (!fromChangePassword) {
            tvMessageNewPasswordEmpty.gone()
            tvMessageConfirmPasswordEmpty.gone()
            tvMessageTokenEmpty.gone()
            val newPassword = tietNewPassword.text.toString().trim()
            val confirmPassword = tietConfirmPassword.text.toString().trim()
            val token = tietToken.text.toString().trim()
            if (newPassword.isEmpty()) {
                tvMessageNewPasswordEmpty.visible()
            }
            if (confirmPassword.isEmpty()) {
                tvMessageConfirmPasswordEmpty.visible()
                tvMessageConfirmPasswordEmpty.text = getString(R.string.confirm_password_empty)
            }
            if (token.isEmpty()) {
                tvMessageTokenEmpty.visible()
            }
            if (token.isNotEmpty() && newPassword.isNotEmpty() && confirmPassword.isNotEmpty()) {
                if (newPassword == confirmPassword) {
                    showProgress()
                    authViewModel.resetPasswordConfirm(
                        PostResetPasswordConfirmUseCase.Params(
                            token,
                            newPassword
                        )
                    )
                } else {
                    tvMessageConfirmPasswordEmpty.visible()
                    tvMessageConfirmPasswordEmpty.text =
                        getString(R.string.confirm_password_not_correct)
                }
            }

//            } else {
//                tvMessageOldPasswordEmpty.gone()
//                tvMessageNewPasswordEmpty.gone()
//                tvMessageConfirmPasswordEmpty.gone()
//
//                val oldPassword = tietOldPassword.text.toString().trim()
//                val newPassword = tietNewPassword.text.toString().trim()
//                val confirmPassword = tietConfirmPassword.text.toString().trim()
//                if (oldPassword.isEmpty()) {
//                    tvMessageOldPasswordEmpty.visible()
//                }
//                if (newPassword.isEmpty()) {
//                    tvMessageNewPasswordEmpty.visible()
//                }
//                if (confirmPassword.isEmpty()) {
//                    tvMessageConfirmPasswordEmpty.visible()
//                    tvMessageConfirmPasswordEmpty.text = getString(R.string.confirm_password_empty)
//                }
//                if (oldPassword.isNotEmpty() && newPassword.isNotEmpty() && confirmPassword.isNotEmpty()) {
//                    if (newPassword == confirmPassword) {
//                        showProgress()
//                        userViewModel.changePassword(
//                            PostChangePasswordUseCase.Params(
//                                oldPassword,
//                                newPassword
//                            )
//                        )
//                    } else {
//                        tvMessageConfirmPasswordEmpty.visible()
//                        tvMessageConfirmPasswordEmpty.text =
//                            getString(R.string.confirm_password_not_correct)
//                    }
//                }
//            }
        }
    }

    override fun loadData() {
        Toast.makeText(appContext,getString(R.string.content_forgot_password),Toast.LENGTH_LONG).show()
    }

    override fun reloadData() {

    }

    private fun onReceiveChangePasswordEntity(entity: ChangePasswordEntity?) {
        hideProgress()
        val callback = object : ChangePasswordSuccessBottomSheet.CallBack {
            override fun onDismiss() {
            }
        }
        mNavigator.showChangePasswordSuccess(activity, callback)
    }

    private fun onReceiveResetPasswordConfirmEntity(entity: ResetPasswordConfirmEntity?) {
        hideProgress()
        if (fromChangePassword) {
            val callback = object : ChangePasswordSuccessBottomSheet.CallBack {
                override fun onDismiss() {
                    mNavigator.showLoginNew(activity)
                }
            }
            mNavigator.showChangePasswordSuccess(activity, callback)
        } else {
            mNavigator.showNewPasswordSuccess(activity)
            finish()
        }
    }
}