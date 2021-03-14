package com.kardia.membership.features.fragments.top_up

import android.os.Bundle
import com.kardia.membership.R
import com.kardia.membership.core.platform.BaseBottomSheetDialogFragment
import com.kardia.membership.core.platform.OnItemClickListener
import com.kardia.membership.data.entities.ConfigItem
import com.kardia.membership.data.entities.TopUpAmount
import com.kardia.membership.data.entities.User
import com.kardia.membership.features.utils.AppConstants
import com.kardia.membership.features.utils.DataConstants
import kotlinx.android.synthetic.main.bottom_sheet_top_up_amount.*
import javax.inject.Inject

class TopUpAmountBottomSheet : BaseBottomSheetDialogFragment() {
    @Inject
    lateinit var topUpAmountAdapter: TopUpAmountAdapter

    private var callback: CallBack? = null

    override fun layoutId(): Int {
        return R.layout.bottom_sheet_top_up_amount
    }

    override fun isFullHeight(): Boolean {
        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
    }

    override fun initViews() {
        topUpAmountAdapter.collection = DataConstants.TOP_UP_AMOUNT_LIST
        rvTopUpAmount.adapter = topUpAmountAdapter.apply {
            onItemClickListener = object : OnItemClickListener {
                override fun onItemClick(item: Any?, position: Int) {
                    DataConstants.TOP_UP_AMOUNT_LIST.forEach {
                        it.isSelected = false
                    }
                    val topUpAmount = item as TopUpAmount
                    topUpAmount.isSelected = true
                    callback?.onItemClick(topUpAmount,position)
                    dismiss()
                }
            }
        }
    }

    override fun initEvents() {
    }

    override fun loadData() {
    }

    override fun reloadData() {

    }

    fun setCallBack(callback: CallBack?) {
        this.callback = callback
    }


    interface CallBack {
        fun onItemClick(topUpAmount: TopUpAmount, position: Int)
    }
}