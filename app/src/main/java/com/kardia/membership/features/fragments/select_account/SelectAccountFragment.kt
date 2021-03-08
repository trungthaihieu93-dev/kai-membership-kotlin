package com.kardia.membership.features.fragments.select_account

import android.os.Bundle
import android.view.View
import com.kardia.membership.R
import com.kardia.membership.core.platform.BaseFragment
import com.kardia.membership.core.platform.OnItemClickListener
import com.kardia.membership.data.entities.PasscodeDevice
import com.kardia.membership.data.entities.User
import kotlinx.android.synthetic.main.fragment_select_account.*
import javax.inject.Inject

class SelectAccountFragment : BaseFragment() {
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
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        passcodeDevice = arguments?.getParcelable(PASSCODE_DEVICE)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun initViews() {
        rvAccount.adapter = selectAccountAdapter.apply {
            onItemClickListener = object : OnItemClickListener {
                override fun onItemClick(item: Any?, position: Int) {
                    val account = item as User
                    mNavigator.showEnterPasscode(activity,account.email)
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
        passcodeDevice?.user?.let {
            if (selectAccountAdapter.collection.size == 0) {
                selectAccountAdapter.collection.addAll(it)
            }
        }
    }

    override fun reloadData() {

    }

}