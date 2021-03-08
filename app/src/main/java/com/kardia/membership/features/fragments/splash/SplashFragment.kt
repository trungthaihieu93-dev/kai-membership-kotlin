package com.kardia.membership.features.fragments.splash

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.telephony.TelephonyManager
import android.view.View
import com.kardia.membership.R
import com.kardia.membership.core.extension.observe
import com.kardia.membership.core.extension.viewModel
import com.kardia.membership.core.platform.BaseFragment
import com.kardia.membership.domain.entities.device.PasscodeDeviceEntity
import com.kardia.membership.features.utils.AppConstants
import com.kardia.membership.features.viewmodel.DeviceViewModel
import java.util.*


class SplashFragment : BaseFragment() {
    private lateinit var deviceViewModel: DeviceViewModel

    override fun layoutId() = R.layout.fragment_splash

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        deviceViewModel = viewModel(viewModelFactory) {
            observe(passcodeDeviceEntity, ::onReceivePasscodeDeviceEntity)
            observe(failureData, ::handleFailure)
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

    }

    override fun initEvents() {

    }

    @SuppressLint("HardwareIds")
    override fun loadData() {
        activity?.let {
            val androidId = Settings.Secure.getString(
                context!!.contentResolver,
                Settings.Secure.ANDROID_ID
            )

            val androidId_UUID: UUID = UUID
                .nameUUIDFromBytes(androidId.toByteArray(charset("utf8")))

            val deviceId: String = androidId_UUID.toString()
            deviceViewModel.getPasscodeByDevice(AppConstants.DEVICE_ID_TEST)
        }
    }

    override fun reloadData() {

    }

    @SuppressLint("HardwareIds")
    fun getDeviceId(context: Context): String? {
        val deviceId: String
        deviceId = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
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
        return deviceId
    }

    private fun onReceivePasscodeDeviceEntity(entity: PasscodeDeviceEntity?) {
        finish()
        entity?.data?.let{
            if(it.user.isNullOrEmpty()){
                mNavigator.showIntroduce(activity)
            }
            else{
                mNavigator.showSelectAccount(activity,it)
            }
        }

    }
}