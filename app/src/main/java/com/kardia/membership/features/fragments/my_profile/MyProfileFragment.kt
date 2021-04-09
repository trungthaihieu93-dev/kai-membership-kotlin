package com.kardia.membership.features.fragments.my_profile

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Bundle
import android.view.View
import com.kardia.membership.R
import com.kardia.membership.core.extension.*
import com.kardia.membership.core.platform.BaseFragment
import com.kardia.membership.domain.entities.user.UpdateUserEntity
import com.kardia.membership.domain.entities.user.UserInfoEntity
import com.kardia.membership.domain.usecases.user.PostUpdateInfoUseCase
import com.kardia.membership.features.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_my_profile.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import java.util.*


class MyProfileFragment : BaseFragment() {
    private lateinit var userViewModel: UserViewModel

    override fun layoutId() = R.layout.fragment_my_profile
    private val myCalendar = Calendar.getInstance()
    private var birthDate: Long? = null
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

        val date =
            OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                myCalendar[Calendar.YEAR] = year
                myCalendar[Calendar.MONTH] = monthOfYear
                myCalendar[Calendar.DAY_OF_MONTH] = dayOfMonth
                etDateOfBirthMyProfile.setText(myCalendar.time.time.getMonthYearDate("dd/MM/yyyy"))
                birthDate = myCalendar.timeInMillis
            }
        etDateOfBirthMyProfile.setOnClickListener {
            activity?.let { activity ->
                DatePickerDialog(
                    activity, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)
                ).show()
            }
        }
    }

    override fun loadData() {
        etFullNameMyProfile.setText(userInfoCache.get()?.kai_info?.first_name)
        etPhoneMyProfile.setText(userInfoCache.get()?.user_info?.phone)
        etDateOfBirthMyProfile.setText(userInfoCache.get()?.user_info?.birthday_time?.getMonthYearDate("dd/MM/yyyy"))
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
                    firstName, phone, birthDate
                )
            )
        }
    }
}