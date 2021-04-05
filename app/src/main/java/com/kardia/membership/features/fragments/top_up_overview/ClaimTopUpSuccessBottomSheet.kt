package com.kardia.membership.features.fragments.top_up_overview

import android.content.DialogInterface
import android.os.Bundle
import com.kardia.membership.R
import com.kardia.membership.core.platform.BaseBottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_sheet_claim_top_up_success.*

class ClaimTopUpSuccessBottomSheet  : BaseBottomSheetDialogFragment() {
    private var callback: CallBack? = null

    override fun layoutId(): Int {
        return R.layout.bottom_sheet_claim_top_up_success
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
        btBackToUtilities.setOnClickListener {
            dismiss()
            callback?.onBackToUtilities()
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        callback?.onDismiss()
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
        fun onBackToUtilities()
    }

}