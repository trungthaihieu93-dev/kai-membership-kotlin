package com.kardia.membership.features.fragments.new_passcode

import android.content.DialogInterface
import android.os.Bundle
import com.kardia.membership.R
import com.kardia.membership.core.platform.BaseBottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_sheet_change_password_success.*

class ChangePasswordSuccessBottomSheet: BaseBottomSheetDialogFragment() {
    private var callback: CallBack? = null

    override fun layoutId(): Int {
        return R.layout.bottom_sheet_change_password_success
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
        btOkIGotIt.setOnClickListener {
            dismiss()
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

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        callback?.onDismiss()
    }
}