package com.kardia.membership.features.fragments.splash

import android.os.Bundle
import android.view.View
import com.kardia.membership.R
import com.kardia.membership.core.platform.BaseFragment

class SplashFragment : BaseFragment() {
    override fun layoutId() = R.layout.fragment_splash

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (firstTimeCreated(savedInstanceState)) {
            finish()
            mNavigator.showIntroduce(activity)
        }
    }

    override fun initViews() {

    }

    override fun initEvents() {

    }

    override fun loadData() {
    }

    override fun reloadData() {

    }

}