package com.kardia.membership.features.fragments.my_profile

import android.os.Bundle
import android.view.View
import com.kardia.membership.R
import com.kardia.membership.core.extension.*
import com.kardia.membership.core.platform.BaseFragment
import com.kardia.membership.domain.entities.user.UpdateUserEntity
import com.kardia.membership.domain.entities.user.UserInfoEntity
import com.kardia.membership.domain.usecases.auth.PostLoginAuthUseCase
import com.kardia.membership.domain.usecases.user.PostUpdateInfoUseCase
import com.kardia.membership.features.utils.AppConstants
import com.kardia.membership.features.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_my_profile.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class MyProfileFragment : BaseFragment() {
    private lateinit var userViewModel: UserViewModel

    override fun layoutId() = R.layout.fragment_my_profile

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        userViewModel = viewModel(viewModelFactory) {
            observe(getUserInfoEntity, ::onReceiveUserInfoEntity)
            observe(updateUserInfoEntity, ::onReceiveUpdateUserInfoEntity)
            observe(failureData, ::handleFailure)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun initViews() {
        ivBack.visible()

        flPaddingTop.visible()
    }

    override fun initEvents() {
        ivBack.setOnClickListener {
            close()
        }

        btUpdateProfile.setOnClickListener {
            updateProfile()
        }
    }

    override fun loadData() {
        etFullNameMyProfile.setText(userInfoCache.get()?.kai_info?.first_name)
        etPhoneMyProfile.setText(userInfoCache.get()?.user_info?.phone)
    }

    override fun reloadData() {

    }

    private fun onReceiveUpdateUserInfoEntity(entity: UpdateUserEntity?) {
        hideProgress()
        userViewModel.getUserInfo()
    }

    private fun onReceiveUserInfoEntity(entity: UserInfoEntity?) {
        forceHide()
        entity?.data?.let {
            userInfoCache.put(it)
            close()
        }
    }

    private fun updateProfile() {
        tvMessageFullNameEmpty.gone()
        tvMessagePhoneEmpty.gone()
        val firstName = etFullNameMyProfile.text.toString().trim()
        val phone = etPhoneMyProfile.text.toString().trim()
        if (firstName.isEmpty()) {
            tvMessageFullNameEmpty.visible()
        }
        if (phone.isEmpty()) {
            tvMessagePhoneEmpty.visible()
        }
        if (firstName.isNotEmpty() && phone.isNotEmpty()) {
            showProgress()
            userViewModel.updateUserInfo(
                PostUpdateInfoUseCase.Params(
                    firstName, phone
                )
            )
        }
    }
}