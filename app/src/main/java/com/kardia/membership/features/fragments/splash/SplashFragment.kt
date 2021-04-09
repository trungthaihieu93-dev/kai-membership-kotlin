package com.kardia.membership.features.fragments.splash

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.telephony.TelephonyManager
import android.view.View
import com.kardia.membership.R
import com.kardia.membership.core.exception.Failure
import com.kardia.membership.core.extension.observe
import com.kardia.membership.core.extension.viewModel
import com.kardia.membership.core.platform.BaseActivity
import com.kardia.membership.core.platform.BaseFragment
import com.kardia.membership.domain.entities.config.ConfigEntity
import com.kardia.membership.domain.entities.device.PasscodeDeviceEntity
import com.kardia.membership.features.dialog.DialogAlert
import com.kardia.membership.features.dialog.NoInternetDialog
import com.kardia.membership.features.utils.AppConstants
import com.kardia.membership.features.utils.AppLog
import com.kardia.membership.features.utils.CommonUtils
import com.kardia.membership.features.utils.DataConstants
import com.kardia.membership.features.viewmodel.ConfigViewModel
import com.kardia.membership.features.viewmodel.DeviceViewModel


class SplashFragment : BaseFragment() {
    private lateinit var deviceViewModel: DeviceViewModel
    private lateinit var configViewModel: ConfigViewModel
    override fun layoutId() = R.layout.fragment_splash

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        deviceViewModel = viewModel(viewModelFactory) {
            observe(passcodeDeviceEntity, ::onReceivePasscodeDeviceEntity)
            observe(failureData, ::handleFailurePasscodeDevice)
        }

        configViewModel = viewModel(viewModelFactory) {
            observe(configEntity, ::onReceiveConfigEntity)
            observe(failureData, ::handleFailureConfig)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        if (firstTimeCreated(savedInstanceState)) {
//            finish()
//            mNavigator.showIntroduce(activity)
//        }
    }

    override fun initViews() {
        resetDataUser()
    }

    override fun initEvents() {

    }

    @SuppressLint("HardwareIds")
    override fun loadData() {
        configViewModel.getConfig()
    }

    override fun reloadData() {

    }

    @SuppressLint("HardwareIds")
    fun getDeviceId(context: Context): String? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            Settings.Secure.getString(
                context.contentResolver,
                Settings.Secure.ANDROID_ID
            )
        } else {
            val mTelephony =
                context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            if (mTelephony.deviceId != null) {
                mTelephony.deviceId
            } else {
                Settings.Secure.getString(
                    context.contentResolver,
                    Settings.Secure.ANDROID_ID
                )
            }
        }
    }

    private fun onReceivePasscodeDeviceEntity(entity: PasscodeDeviceEntity?) {
        forceHide()
        entity?.data?.let{
            DataConstants.PASSCODE_DEVICE = it
            if(it.user.isNullOrEmpty()){
                mNavigator.showIntroduce(activity)
            }
            else{
                mNavigator.showSelectAccount(activity, it)
            }
        }
        finish()
    }

    private fun onReceiveConfigEntity(entity: ConfigEntity?) {
        entity?.let{
            configCache.put(it)
            activity?.let {
                deviceViewModel.getPasscodeByDevice(AppConstants.DEVICE_ID)
            }
        }
    }

    private fun handleFailureConfig(failure: Failure?) {
        activity?.let {
            deviceViewModel.getPasscodeByDevice(AppConstants.DEVICE_ID)
        }
    }

    private fun handleFailurePasscodeDevice(failure: Failure?) {
        forceHide()
        mNavigator.showIntroduce(activity)
    }
}