package com.kardia.membership.core.platform

import android.app.Activity
import android.app.Dialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.DisplayMetrics
import android.view.*
import android.widget.FrameLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.kardia.membership.AndroidApplication
import com.kardia.membership.R
import com.kardia.membership.core.di.ApplicationComponent
import com.kardia.membership.core.exception.Failure
import com.kardia.membership.core.navigation.Navigator
import com.kardia.membership.data.cache.*
import com.kardia.membership.features.utils.AppLog
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kardia.membership.features.dialog.DialogAlert
import com.kardia.membership.features.dialog.NoInternetDialog
import com.kardia.membership.features.utils.CommonUtils
import java.io.File
import java.util.*
import javax.inject.Inject

abstract class BaseBottomSheetDialogFragment : BottomSheetDialogFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var userTokenCache: UserTokenCache

    @Inject
    internal lateinit var mNavigator: Navigator

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    @Inject
    lateinit var application: AndroidApplication

    abstract fun layoutId(): Int

    abstract fun isFullHeight(): Boolean

    private var isExpired: Boolean = false

    val appComponent: ApplicationComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        (activity?.application as AndroidApplication).appComponent
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
    }

    //    override fun getTheme(): Int {
//        return R.style.Widget_AppTheme_BottomSheet
//    }
    fun userLoggedIn(): Boolean {
        return !userTokenCache.get()?.accessToken.isNullOrEmpty()
    }

    override fun onGetLayoutInflater(savedInstanceState: Bundle?): LayoutInflater {
        val inflater = super.onGetLayoutInflater(savedInstanceState)
        val wrappedContext =
            ContextThemeWrapper(requireContext(), R.style.Widget_AppTheme_BottomSheet)
        return inflater.cloneInContext(wrappedContext)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        inflater.inflate(layoutId(), container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (isFullHeight()) {
            view.viewTreeObserver.addOnGlobalLayoutListener(object :
                ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    view.viewTreeObserver.removeOnGlobalLayoutListener(this)
                    val dialog = dialog as BottomSheetDialog
                    val bottomSheet =
                        dialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout?
                    val behavior = BottomSheetBehavior.from(bottomSheet!!)
                    behavior.state = BottomSheetBehavior.STATE_EXPANDED
                    behavior.peekHeight = 0
                    context?.let {
                        bottomSheet.setBackgroundColor(
                            ContextCompat.getColor(
                                it,
                                android.R.color.transparent
                            )
                        )
                    }

                    behavior.setBottomSheetCallback(object :
                        BottomSheetBehavior.BottomSheetCallback() {
                        override fun onStateChanged(bottomSheet: View, newState: Int) {
                            if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                                dismiss()
                            }
                        }

                        override fun onSlide(bottomSheet: View, slideOffset: Float) {}
                    })
                }
            })
        }
        forceHide()
    }

    internal fun forceHide() =
        activity?.let { (it as BaseActivity).forceHide() }

    private fun setupFullHeight(bottomSheetDialog: BottomSheetDialog) {
        val bottomSheet =
            bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout?
        val behavior = BottomSheetBehavior.from(bottomSheet!!)
        val layoutParams = bottomSheet.layoutParams
        val windowHeight = getWindowHeight()
        if (layoutParams != null) {
            layoutParams.height = windowHeight
        }
        bottomSheet.layoutParams = layoutParams
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
        behavior.isDraggable = false
    }

    private fun getWindowHeight(): Int {
        // Calculate window height for fullscreen use
        val displayMetrics = DisplayMetrics()
        (context as Activity?)!!.windowManager.defaultDisplay
            .getMetrics(displayMetrics)
        return displayMetrics.heightPixels
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        if (isFullHeight()) {
            dialog.setOnShowListener { dialogInterface ->
                val bottomSheetDialog = dialogInterface as BottomSheetDialog
                setupFullHeight(bottomSheetDialog)
            }
        }
        return dialog
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
                            .onPositive { onReloadData() }.show(requireContext())
                    }
                }
            }
            is Failure.ServerError -> {
                AppLog.e("Duy", "${failure.error} : ${(activity as BaseActivity).isShowError}")
                if (failure.errorCode == 401 || failure.errorCode == 403) {
                    DialogAlert()
                        .setTitle("This account is already logged into on another device.")
                        .setMessage("You are currently logged in on another device. Please log out of the other device or contact your administrator.")
                        .setCancel(false)
                        .setTitlePositive("OK")
                        .onPositive {
//                            logOut()
                            mNavigator.showMain(activity)
                        }
                        .show(requireContext())
                    hideProgress()
                } else {
                    if (failure.error.contains("The server is down")) {
                        if (!(activity as BaseActivity).isShowNoInternet) {
                            (activity as BaseActivity).isShowNoInternet = true
                            activity?.let {
//                                NoInternetDialog().apply {
//                                    onDismiss {
//                                        (activity as BaseActivity).isShowNoInternet = false
//                                    }
//                                }
//                                    .onPositive { onReloadData() }.show(requireContext())
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
                            CommonUtils.showError(
                                activity,
                                getString(R.string.announcement),
                                "${failure.error}"
                            )
                        }
                    }
                }
            }
        }
        BaseActivity.apiRequestCount = 0
        hideProgress()
    }

//    fun refreshToken() {
//        if (!isExpired) {
//            isExpired = true
//            userTokenCache.get()?.accessToken = ""
//            userTokenCache.get()?.accessToken?.let {
//                refreshTokenViewModel.refreshToken(
//                    it
//                )
//            }
//        }
//    }

    private fun handleFailureRefresh(failure: Failure?) {
        DialogAlert()
            .setTitle("This account is already logged into on another device.")
            .setMessage("You are currently logged in on another device. Please log out of the other device or contact your administrator.")
            .setCancel(false)
            .setTitlePositive("OK")
            .onPositive {
                mNavigator.showMain(activity)
            }
            .show(requireContext())
        hideProgress()
    }

    open fun onReloadData() {}


    fun isNetworkAvailable(context: Context?): Boolean {
        if (context == null) return false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        }
        return false
    }

    fun isBeforeToday(month: Int, date: Int, year: Int): Boolean {
        val today = Calendar.getInstance()
        if (today.get(Calendar.YEAR) > year) {
            return true
        } else if (today.get(Calendar.YEAR) == year) {
            if (today.get(Calendar.MONTH) > month) {
                return true
            } else if (today.get(Calendar.MONTH) == month) {
                if (today.get(Calendar.DATE) > date) {
                    return true
                }
            }
        }
        return false
    }

    fun copyTextToClipboard(text: String) {
        val clipboardManager =
            activity?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("text", text)
        clipboardManager.setPrimaryClip(clipData)
        Toast.makeText(activity, "Đã sao chép vào bộ nhớ tạm", Toast.LENGTH_SHORT).show()
    }

    internal fun showProgress() = activity?.let { (it as BaseActivity).showProgress() }

    internal fun forceShowProgress() = activity?.let { (it as BaseActivity).forceShowProgress() }

    internal fun hideProgress(delayTime: Int = 0) =
        activity?.let { (it as BaseActivity).hideProgress(delayTime) }

    fun showMessage(title: String?, message: String) {
        DialogAlert()
            .setTitle(if (!TextUtils.isEmpty(title)) title!! else "")
            .setMessage(message)
            .setCancel(false)
            .setTitlePositive("OK")
            .show(requireContext())
    }

    fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun showDialogAnnouncement(message: String) {
        context?.let {
            DialogAlert()
                .setTitle(it.resources.getString(R.string.announcement))
                .setMessage(message)
                .setCancel(false)
                .setTitlePositive("OK")
                .onPositive {
                    dismiss()
                }
                .show(it)
        }
    }
}

