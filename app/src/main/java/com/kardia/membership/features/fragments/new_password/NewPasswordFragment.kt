package com.kardia.membership.features.fragments.new_password

import android.os.Bundle
import android.view.View
import com.kardia.membership.R
import com.kardia.membership.core.extension.*
import com.kardia.membership.core.platform.BaseFragment
import com.kardia.membership.domain.entities.user.ChangePasswordEntity
import com.kardia.membership.domain.usecases.user.PostChangePasswordUseCase
import com.kardia.membership.features.fragments.new_passcode.ChangePasswordSuccessBottomSheet
import com.kardia.membership.features.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_new_password.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class NewPasswordFragment : BaseFragment() {
    private lateinit var userViewModel: UserViewModel

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
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.getBoolean(FROM_CHANGE_PASSWORD, false)?.let {
            fromChangePassword = it
        }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun initViews() {
        ivBack.visible()
        flPaddingTop.visible()
        llToken.gone()
        llOldPassword.gone()
        if (!fromChangePassword) {
            llToken.visible()
        } else {
            llOldPassword.visible()
        }
    }

    override fun initEvents() {
        ivBack.setOnClickListener {
            close()
        }

        btSetNewPassword.setOnClickListener {
            if (!fromChangePassword) {
                mNavigator.showNewPasswordSuccess(activity)
                finish()
            } else {
                tvMessageOldPasswordEmpty.gone()
                tvMessageNewPasswordEmpty.gone()
                tvMessageConfirmPasswordEmpty.gone()

                val oldPassword = tietOldPassword.text.toString().trim()
                val newPassword = tietNewPassword.text.toString().trim()
                val confirmPassword = tietConfirmPassword.text.toString().trim()
                if (oldPassword.isEmpty()) {
                    tvMessageOldPasswordEmpty.visible()
                }
                if (newPassword.isEmpty()) {
                    tvMessageNewPasswordEmpty.visible()
                }
                if (confirmPassword.isEmpty()) {
                    tvMessageConfirmPasswordEmpty.visible()
                    tvMessageConfirmPasswordEmpty.text = getString(R.string.confirm_password_empty)
                }
                if (oldPassword.isNotEmpty() && newPassword.isNotEmpty() && confirmPassword.isNotEmpty()) {
                    if (newPassword == confirmPassword) {
                        showProgress()
                        userViewModel.changePassword(
                            PostChangePasswordUseCase.Params(
                                oldPassword,
                                newPassword
                            )
                        )
                    } else {
                        tvMessageConfirmPasswordEmpty.visible()
                        tvMessageConfirmPasswordEmpty.text =
                            getString(R.string.confirm_password_not_correct)
                    }
                }
            }
        }
    }

    override fun loadData() {
    }

    override fun reloadData() {

    }

    private fun onReceiveChangePasswordEntity(entity: ChangePasswordEntity?) {
        hideProgress()
        val callback = object : ChangePasswordSuccessBottomSheet.CallBack {
            override fun onDismiss() {
            }
        }
        mNavigator.showChangePasswordSuccess(activity,callback)
    }

}