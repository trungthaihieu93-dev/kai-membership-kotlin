package com.kardia.membership.core.platform

import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.kardia.membership.AndroidApplication
import com.kardia.membership.R
import com.kardia.membership.core.di.ApplicationComponent
import com.kardia.membership.core.exception.Failure
import com.kardia.membership.features.dialog.DialogAlert
import com.kardia.membership.features.dialog.NoInternetDialog
import com.kardia.membership.features.utils.CommonUtils
import javax.inject.Inject

open class BaseDialog : DialogFragment() {
    private var baseActivity: BaseActivity? = null

    val appComponent: ApplicationComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        (activity?.application as AndroidApplication).appComponent
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity) {
            val mActivity = context as BaseActivity?
            this.baseActivity = mActivity
        }
    }

    internal fun showProgress() = activity?.let { (it as BaseActivity).showProgress() }

    internal fun hideProgress(delayTime: Int = 0) =
        activity?.let { (it as BaseActivity).hideProgress(delayTime) }

    override fun onDetach() {
        baseActivity = null
        super.onDetach()
    }

    open fun show(context: Context, tag: String) {
        try {
            if (context is AppCompatActivity) {
                val manager = context.supportFragmentManager
                val transaction = manager.beginTransaction()
                val prevFragment = manager.findFragmentByTag(tag)
                if (prevFragment != null) {
                    transaction.remove(prevFragment)
                }
                transaction.addToBackStack(null)
                val ft = manager.beginTransaction()
                ft.add(this, tag)
                ft.commitAllowingStateLoss()
            }
        }
        catch (e: Exception){
//            FirebaseCrashlytics.getInstance().recordException(e)
        }
    }

    open fun showNoDuplicate(context: Context, tag: String) {
        if (context is AppCompatActivity) {
            val manager = context.supportFragmentManager
            val transaction = manager.beginTransaction()
            val prevFragment = manager.findFragmentByTag(tag)
            if (prevFragment != null) {
                return
            }
            transaction.addToBackStack(null)
            super.show(manager, tag)
        }
    }

    open fun handleFailure(failure: Failure?) {
        when (failure) {
            is Failure.NetworkConnection -> {
                if (!(activity as BaseActivity).isShowNoInternet) {
                    (activity as BaseActivity).isShowNoInternet = true
                    activity?.let {
                        NoInternetDialog().apply {
                            onDismiss {
                                (activity as BaseActivity).isShowNoInternet = false
                            }
                        }
                            .onNegative { onReloadData() }
                            .onPositive { }.show(requireContext())

                    }
                }
            }
            is Failure.ServerError -> {
                if (failure.errorCode == 401 || failure.errorCode == 403) {

                    hideProgress()
                } else {
                    if (failure.error.contains("The server is down")) {
                        if (!(activity as BaseActivity).isShowNoInternet) {
                            (activity as BaseActivity).isShowNoInternet = true
                            activity?.let {
                            }
                        }
                    } else if (!(activity as BaseActivity).isShowError) {
                        (activity as BaseActivity).isShowError = true
                        if (failure.error.contains("The server is busy. Please try again.")) {
                            DialogAlert().setTitle("Sorry!")
                                .setMessage("${failure.error}")
                                .setTitlePositive("Retry")
                                .onPositive { onReloadData() }
                                .onDismiss { (activity as BaseActivity).isShowError = false }
                                .show(requireContext())
                        } else {
                            CommonUtils.showError(activity, getString(R.string.announcement), "${failure.error}")
                        }
                    }
                }
            }
        }
        hideProgress()
    }

    open fun onReloadData() {}

    fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}