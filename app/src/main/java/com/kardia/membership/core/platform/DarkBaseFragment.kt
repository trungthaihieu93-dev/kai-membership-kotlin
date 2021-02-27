package com.kardia.membership.core.platform

import android.os.Bundle
import android.view.View

abstract class DarkBaseFragment : BaseFragment() {
    override fun onResume() {
        super.onResume()
//        (activity as BaseActivity).changeFullScreenMode()
    }
}