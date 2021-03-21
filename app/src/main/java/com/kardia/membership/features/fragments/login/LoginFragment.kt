package com.kardia.membership.features.fragments.login

import android.os.Bundle
import android.view.View
import com.kardia.membership.R
import com.kardia.membership.core.extension.gone
import com.kardia.membership.core.extension.observe
import com.kardia.membership.core.extension.viewModel
import com.kardia.membership.core.extension.visible
import com.kardia.membership.core.platform.BaseFragment
import com.kardia.membership.data.entities.UserToken
import com.kardia.membership.domain.entities.auth.LoginAuthEntity
import com.kardia.membership.domain.entities.user.UserInfoEntity
import com.kardia.membership.domain.usecases.auth.PostLoginAuthUseCase
import com.kardia.membership.features.utils.AppConstants
import com.kardia.membership.features.viewmodel.AuthViewModel
import com.kardia.membership.features.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : BaseFragment() {
    private lateinit var authViewModel: AuthViewModel
    private lateinit var userViewModel: UserViewModel
    override fun layoutId() = R.layout.fragment_login

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        authViewModel = viewModel(viewModelFactory) {
            observe(loginEntity, ::onReceiveLoginAuthEntity)
            observe(failureData, ::handleFailure)
        }

        userViewModel = viewModel(viewModelFactory) {
            observe(getUserInfoEntity, ::onReceiveUserInfoEntity)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun initViews() {
        resetDataUser()
    }

    override fun initEvents() {
        tvCreateAccount.setOnClickListener {
            mNavigator.showRegister(activity)
        }

        tvForgotPassword.setOnClickListener {
            mNavigator.showForgotPassword(activity)
        }

        btSignIn.setOnClickListener {
            login()
        }

        btSkip.setOnClickListener {
            mNavigator.showMain(activity)
        }
    }

    override fun loadData() {
    }

    override fun reloadData() {

    }

    private fun login() {
        tvMessageEmailEmpty.gone()
        tvMessagePasswordEmpty.gone()
        val email = tietEmail.text.toString().trim()
        val password = tietPassword.text.toString().trim()
        if (email.isEmpty()) {
            tvMessageEmailEmpty.visible()
        }
        if (password.isEmpty()) {
            tvMessagePasswordEmpty.visible()
        }
        if (email.isNotEmpty() && password.isNotEmpty()) {
            showProgress()
            authViewModel.loginAuth(
                PostLoginAuthUseCase.Params(
                    email,
                    password,
                    AppConstants.DEVICE_OS
                )
            )
        }
    }

    private fun onReceiveLoginAuthEntity(entity: LoginAuthEntity?) {
        hideProgress()
        entity?.data?.let {
            userTokenCache.put(UserToken(it.access_token, it.refresh_token, it.expires_in,it.is_first))
            userViewModel.getUserInfo()
        }
    }

    private fun onReceiveUserInfoEntity(entity: UserInfoEntity?) {
        entity?.data?.let {
            userInfoCache.put(it)
            mNavigator.showEnterPasscode(activity, it.user_info?.email)
        }
    }
}