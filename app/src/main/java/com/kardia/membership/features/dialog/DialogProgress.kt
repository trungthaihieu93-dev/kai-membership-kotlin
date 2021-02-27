package com.kardia.membership.features.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import com.kardia.membership.R
import com.kardia.membership.core.extension.close
import com.kardia.membership.core.platform.BaseActivity
import com.kardia.membership.core.platform.BaseDialog
import kotlinx.android.synthetic.main.dialog_progress.view.*


open class DialogProgress constructor(val gifDrawable: pl.droidsonroids.gif.GifDrawable) :
    BaseDialog() {

    private var isDim: Boolean = false

    fun show(context: Context) {
        super.show(context, TAG)
    }


    companion object {
        private val TAG = DialogProgress::class.java.simpleName
        fun hide(context: BaseActivity) {
            try {
                context.supportFragmentManager.findFragmentByTag(TAG)
                    ?.let { (it as DialogFragment).dismissAllowingStateLoss() }
            } catch (e: Exception) {

            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(context!!, R.style.DialogDimTheme)
//        dialog.window?.setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        if (isDim) {
            dialog.window?.setDimAmount(0.5f)
        } else {
            dialog.window?.setDimAmount(0f)
        }
        dialog.setOnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                BaseActivity.apiRequestCount = 0
                (activity as BaseActivity).isShowProgress = false
                dialog.dismiss()
                if (!(activity as BaseActivity).defaultBack()) {
                    (activity as BaseActivity).getCurrentFragment().close()
                }
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }
        return dialog
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_progress, container, false)
        view.loading_gif.setImageDrawable(gifDrawable)
        return view
    }
}