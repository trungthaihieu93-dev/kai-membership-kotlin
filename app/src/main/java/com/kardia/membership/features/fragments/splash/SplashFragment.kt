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
import com.kardia.membership.domain.entities.config.ConfigEntity
import com.kardia.membership.domain.entities.device.PasscodeDeviceEntity
import com.kardia.membership.features.utils.AppConstants
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
            observe(failureData, ::handleFailure)
        }

        configViewModel = viewModel(viewModelFactory) {
            observe(configEntity, ::onReceiveConfigEntity)
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
        resetDataUser()
    }

    override fun initEvents() {

    }

    @SuppressLint("HardwareIds")
    override fun loadData() {
        activity?.let {
            deviceViewModel.getPasscodeByDevice(AppConstants.DEVICE_ID)
//            deviceViewModel.getPasscodeByDevice("983360F3DA2D7AEC")
//            deviceViewModel.getPasscodeByDevice("3c9a8afd7a3463c4")

//            val m_wm = it.getSystemService(Context.WIFI_SERVICE) as WifiManager?
//            val m_wlanMacAdd = m_wm!!.connectionInfo.macAddress
//
//            m_wlanMacAdd?.let{ id->
//                deviceViewModel.getPasscodeByDevice(id)
//            }

//            val m_BluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
//            val m_bluetoothAdd = m_BluetoothAdapter.address
//            m_bluetoothAdd?.let{ id->
//                deviceViewModel.getPasscodeByDevice(id)

//            val TelephonyMgr = it.getSystemService(TELEPHONY_SERVICE) as TelephonyManager?
//            val m_deviceId = TelephonyMgr!!.deviceId
//            m_deviceId?.let{ id->
//                deviceViewModel.getPasscodeByDevice(id)
//            }

//            val androidId = Settings.Secure.getString(
//                context!!.contentResolver,
//                Settings.Secure.ANDROID_ID
//            )
//
//            val androidId_UUID: UUID = UUID
//                .nameUUIDFromBytes(androidId.toByteArray(charset("utf8")))
//
//            val unique_id: String = androidId_UUID.toString()
//            unique_id?.let{ id->
//                deviceViewModel.getPasscodeByDevice(id)
//            }
        }
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
        }
        finish()
    }
}