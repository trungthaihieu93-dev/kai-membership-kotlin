package com.kardia.membership.features.fragments.forgot_password

import android.os.Bundle
import android.view.View
import com.kardia.membership.R
import com.kardia.membership.core.extension.observe
import com.kardia.membership.core.extension.viewModel
import com.kardia.membership.core.extension.visible
import com.kardia.membership.core.platform.BaseFragment
import com.kardia.membership.data.entities.UserToken
import com.kardia.membership.domain.entities.auth.LoginAuthEntity
import com.kardia.membership.domain.entities.auth.ResetPasswordEntity
import com.kardia.membership.domain.usecases.auth.PostResetPasswordUseCase
import com.kardia.membership.features.viewmodel.AuthViewModel
import kotlinx.android.synthetic.main.fragment_forgot_password.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class ForgotPasswordFragment : BaseFragment() {
    private lateinit var authViewModel: AuthViewModel

    override fun layoutId() = R.layout.fragment_forgot_password

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        authViewModel = viewModel(viewModelFactory) {
            observe(resetPasswordEntity, ::onReceiveResetPasswordEntity)
            observe(failureData, ::handleFailure)
        }
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
        btSendInstructions.setOnClickListener {
            val email = tietEmail.text.toString().trim()
            if (email.isEmpty()) {
                tvMessageEmailEmpty.visible()
            }
            if (email.isNotEmpty()) {
                showProgress()
                authViewModel.resetPassword(PostResetPasswordUseCase.Params(email))
            }
        }
        ivBack.setOnClickListener {
            finish()
        }
    }

    override fun loadData() {
    }

    override fun reloadData() {

    }

    private fun onReceiveResetPasswordEntity(entity: ResetPasswordEntity?) {
        hideProgress()
        mNavigator.showNewPassword(activity)
    }
}