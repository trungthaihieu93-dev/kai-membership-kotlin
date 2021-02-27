package com.kardia.membership.core.platform

abstract class LightBaseFragment : BaseFragment() {
    override fun onResume() {
        super.onResume()
        (activity as BaseActivity).changeFullScreenMode(false)
    }
}