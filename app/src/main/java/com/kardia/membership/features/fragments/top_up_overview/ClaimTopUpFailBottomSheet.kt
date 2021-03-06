package com.kardia.membership.features.fragments.top_up_overview

import android.os.Bundle
import com.kardia.membership.R
import com.kardia.membership.core.platform.BaseBottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_sheet_claim_top_up_fail.*

class ClaimTopUpFailBottomSheet : BaseBottomSheetDialogFragment() {
    private var callback: CallBack? = null

    override fun layoutId(): Int {
        return R.layout.bottom_sheet_claim_top_up_fail
    }

    override fun isFullHeight(): Boolean {
        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
    }

    override fun initViews() {
    }

    override fun initEvents() {
        btCheckAgain.setOnClickListener {
            dismiss()
            callback?.onDismiss()
        }
    }

    override fun loadData() {
    }

    override fun reloadData() {

    }

    fun setCallBack(callback: CallBack?) {
        this.callback = callback
    }


    interface CallBack {
        fun onDismiss()
    }
}