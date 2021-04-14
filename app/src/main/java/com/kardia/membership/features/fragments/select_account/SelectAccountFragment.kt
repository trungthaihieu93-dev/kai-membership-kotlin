package com.kardia.membership.features.fragments.select_account

import android.os.Bundle
import android.view.View
import com.kardia.membership.R
import com.kardia.membership.core.extension.observe
import com.kardia.membership.core.extension.viewModel
import com.kardia.membership.core.platform.BaseFragment
import com.kardia.membership.core.platform.OnItemClickListener
import com.kardia.membership.data.entities.PasscodeDevice
import com.kardia.membership.data.entities.User
import com.kardia.membership.domain.entities.device.PasscodeDeviceEntity
import com.kardia.membership.features.utils.AppConstants
import com.kardia.membership.features.viewmodel.DeviceViewModel
import kotlinx.android.synthetic.main.fragment_select_account.*
import javax.inject.Inject

class SelectAccountFragment : BaseFragment() {
    private lateinit var deviceViewModel: DeviceViewModel

    @Inject
    lateinit var selectAccountAdapter: SelectAccountAdapter

    private var passcodeDevice: PasscodeDevice? = null

    companion object {
        const val PASSCODE_DEVICE = "passcodeDevice"
    }

    override fun layoutId() = R.layout.fragment_select_account

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        deviceViewModel = viewModel(viewModelFactory) {
            observe(passcodeDeviceEntity, ::onReceivePasscodeDeviceEntity)
            observe(failureData, ::handleFailure)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        passcodeDevice = arguments?.getParcelable(PASSCODE_DEVICE)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun initViews() {
        resetDataUser()
        rvAccount.adapter = selectAccountAdapter.apply {
            onItemClickListener = object : OnItemClickListener {
                override fun onItemClick(item: Any?, position: Int) {
                    val account = item as User
//                    if (!account.refreshToken.isNullOrBlank()) {
                        mNavigator.showEnterPasscode(activity, account.email)
//                    } else {
//                        mNavigator.showCreatePasscode(activity, account.email)
//                    }
                }
            }
        }
    }

    override fun initEvents() {
        tvLogin.setOnClickListener {
            mNavigator.showLogin(activity)
        }
    }

    override fun loadData() {
        if (passcodeDevice != null) {
            passcodeDevice?.user?.let {
                if (selectAccountAdapter.collection.size == 0) {
                    selectAccountAdapter.collection.addAll(it)
                }
            }
        } else {
            showProgress()
            deviceViewModel.getPasscodeByDevice(AppConstants.DEVICE_ID)
        }
    }

    override fun reloadData() {
        loadData()
    }

    private fun onReceivePasscodeDeviceEntity(entity: PasscodeDeviceEntity?) {
        hideProgress()
        entity?.data?.let { pd ->
            passcodeDevice = pd
            passcodeDevice?.user?.let {
                if (selectAccountAdapter.collection.size == 0) {
                    selectAccountAdapter.collection = it as ArrayList<User>
                }
            }
        }
    }
}