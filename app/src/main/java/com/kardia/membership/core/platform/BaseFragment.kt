package com.kardia.membership.core.platform

import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.*
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.util.TypedValue
import android.view.*
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.github.dhaval2404.imagepicker.ImagePicker
import com.github.dhaval2404.imagepicker.util.FileUriUtils
import com.kardia.membership.AndroidApplication
import com.kardia.membership.R
import com.kardia.membership.core.di.ApplicationComponent
import com.kardia.membership.core.exception.Failure
import com.kardia.membership.core.extension.*
import com.kardia.membership.core.navigation.Navigator
import com.kardia.membership.data.cache.*
import com.kardia.membership.features.dialog.DialogAlert
import com.kardia.membership.features.utils.AppLog
import com.google.android.material.snackbar.Snackbar
import com.kardia.membership.features.dialog.NoInternetDialog
import com.kardia.membership.features.utils.CommonUtils
import java.io.File
import javax.inject.Inject

/**
 * Base Fragment class with helper methods for handling views and back button events.
 *
 * @see Fragment
 */
abstract class BaseFragment : Fragment() {

    abstract fun layoutId(): Int

    var statusBarHeight: Int = 0

    private var isExpired: Boolean = false
    var mFragmentLevel = 1

    val appComponent: ApplicationComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        (activity?.application as AndroidApplication).appComponent
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var userTokenCache: UserTokenCache

    @Inject
    lateinit var userInfoCache: UserInfoCache

    @Inject
    lateinit var configCache: ConfigCache

    @Inject
    internal lateinit var mNavigator: Navigator

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    @Inject
    lateinit var application: AndroidApplication

//    var userID = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        inflater.inflate(layoutId(), container, false)

    open fun onBackPressed(): Boolean {
        return false
    }

    protected abstract fun initViews()
    protected abstract fun initEvents()
    protected abstract fun loadData()
    protected abstract fun reloadData()


    fun changeColorStatusBar(activity: FragmentActivity?, color: Int = R.color.white) {
        activity?.let {
            val window: Window = it.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = ContextCompat.getColor(it, color)
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }

    fun getMinutesFromReminderSelection(position: Int): Int {
        return when (position) {
            0 -> 30
            1 -> 60
            2 -> 120
            3 -> 180
            4 -> 1440
            5 -> 2880
            else -> 1
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        getStatusBarHeight()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        userTokenCache.get()?.userID?.let { userID = it }
        super.onViewCreated(view, savedInstanceState)
        forceHide()
        initViews()
        initEvents()
        loadData()
    }

    private fun getStatusBarHeight() {
        val typeValue = TypedValue()
        resources.getValue(
            resources.getIdentifier(
                "status_bar_height", "dimen", "android"
            ), typeValue, true
        )
        statusBarHeight = TypedValue.complexToFloat(typeValue.data).toInt()
        if (statusBarHeight < 0) statusBarHeight = 24.dp2px
    }

    internal fun firstTimeCreated(savedInstanceState: Bundle?) = savedInstanceState == null

    internal fun showProgress() = activity?.let { (it as BaseActivity).showProgress() }

    internal fun hideProgress(delayTime: Int = 100) =
        activity?.let { (it as BaseActivity).hideProgress(delayTime) }

    internal fun forceHide() =
        activity?.let { (it as BaseActivity).forceHide() }

    internal fun notify(@StringRes message: Int) =
        Snackbar.make(viewContainer, message, Snackbar.LENGTH_SHORT).show()

    fun getFragmentLevel(): Int {
        return mFragmentLevel
    }

    fun setFragmentLevel(fragmentLevel: Int) {
        this.mFragmentLevel = fragmentLevel
    }

    private fun getTransactionName(): String {
        return "Level$mFragmentLevel"
    }

    companion object {

        private const val RC_EXPIRED = 34421
        private const val FRAGMENT_LEVEL_1 = "Level1"
        const val TRANSITION_DELAY = 100L

        @SuppressLint("StaticFieldLeak")
        var performClickLogin: View? = null //For resume the user action after login
        var isPopBackStack = false
        var isSetFragmentInProcess = false
        var disableFragmentAnimation = false


        fun addFragmentByActivity(
            activity: FragmentActivity?,
            fragment: BaseFragment,
            containerViewId: Int = R.id.fragmentContainer,
            fromBottomAnimation: Boolean = false
        ) {
            if (activity == null) {
                return
            }
            addFragment(
                activity.supportFragmentManager,
                fragment,
                containerViewId,
                fromBottomAnimation
            )
        }

        /**
         * Add fragment with higher level
         * @param fromBottomAnimation default is FALSE: fragment will appear from right to left; TRUE: fragment will appear from bottom to top
         */
        fun addFragment(
            fragmentManager: FragmentManager,
            fragment: BaseFragment,
            containerViewId: Int = R.id.fragmentContainer,
            fromBottomAnimation: Boolean = false
        ) {
            //Get current fragment level
            val currentFragment = fragmentManager.findFragmentById(containerViewId)
            var currentLevel = 1
            if (currentFragment != null) {
                currentFragment as BaseFragment
                currentLevel = currentFragment.getFragmentLevel() + 1
            }
            fragment.setFragmentLevel(currentLevel)

            if (fromBottomAnimation) {
                setFragment(
                    fragmentManager,
                    fragment,
                    R.id.fragmentContainer,
                    R.anim.slide_up,
                    R.anim.fade_out,
                    R.anim.fade_in,
                    R.anim.slide_down
                )
            } else {
                setFragment(fragmentManager, fragment, containerViewId)
            }
        }

        fun setFragment(
            fragmentManager: FragmentManager,
            fragment: BaseFragment,
            containerViewId: Int = R.id.fragmentContainer,
            animInEnter: Int = R.anim.enter_from_right,
            animInExit: Int = R.anim.exit_to_left,
            animOutEnter: Int = R.anim.enter_from_left,
            animOutExit: Int = R.anim.exit_to_right
        ) {
            if (isSetFragmentInProcess) {
                return
            }
            isSetFragmentInProcess = true
            Handler().postDelayed({
                popBackStack(fragmentManager, fragment)

                val fragmentTransaction = fragmentManager.beginTransaction()
                if (FRAGMENT_LEVEL_1 != fragment.getTransactionName()) {
                    fragmentTransaction.setCustomAnimations(
                        animInEnter,
                        animInExit,
                        animOutEnter,
                        animOutExit
                    )
                } else {
                    fragmentTransaction.setCustomAnimations(0, animInExit, 0, 0)
                }
                fragmentTransaction.replace(containerViewId, fragment)
                try {
                    fragmentTransaction.addToBackStack(fragment.getTransactionName())
                        .commitAllowingStateLoss()
                } catch (e: Exception) {
                }
                isPopBackStack = false
                isSetFragmentInProcess = false
            }, TRANSITION_DELAY)
        }

        // Avoid reloading parent when try to replace same level fragment
        private fun handlePopBackStack(
            fragmentManager: FragmentManager,
            destinationFragment: BaseFragment
        ) {
            try {
                val currentLevel =
                    (fragmentManager.findFragmentById(R.id.fragmentContainer) as BaseFragment).getFragmentLevel()
                if (destinationFragment.getFragmentLevel() <= currentLevel) {
                    isPopBackStack = true
                }
            } catch (e: Exception) {
            }
        }

        @SuppressLint("CommitTransaction")
        fun popBackStack(
            fragmentManager: FragmentManager,
            fragment: BaseFragment,
            disableAnimation: Boolean = false
        ) {
            handlePopBackStack(fragmentManager, fragment)
            if (fragmentManager.backStackEntryCount == 0) {
                return
            }
            try {
                if (disableAnimation) {
                    disableFragmentAnimation = true
                    fragmentManager.popBackStackImmediate(
                        fragment.getTransactionName(),
                        androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE
                    )
                    disableFragmentAnimation = false
                } else {
                    fragmentManager.popBackStackImmediate(
                        fragment.getTransactionName(),
                        FragmentManager.POP_BACK_STACK_INCLUSIVE
                    )
                    if (FRAGMENT_LEVEL_1 == fragment.getTransactionName()) {
                        fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.enter_from_left, 0, 0, 0)
                    }
                }
            } catch (e: Exception) {
            }
        }

        fun pop(activity: FragmentActivity?) {
            if (activity == null) {
                return
            }
            popBackStack(
                activity!!.supportFragmentManager,
                (activity as BaseActivity).getCurrentFragment()
            )
            isPopBackStack = false
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
                            .onNegative { reloadData() }
                            .onPositive { }.show(requireContext())
                    }
                }
            }
            is Failure.ServerError -> {
                AppLog.e("Duy", "${failure.error} : ${(activity as BaseActivity).isShowError}")
                if (failure.errorCode == 401) {
                    DialogAlert()
                        .setTitle("This account is already logged into on another device.")
                        .setMessage("You are currently logged in on another device. Please log out of the other device or contact your administrator.")
                        .setCancel(false)
                        .setTitlePositive("OK")
                        .onPositive {
                            mNavigator.showSelectAccountNew(activity)
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
                                .onPositive { reloadData() }
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_EXPIRED) {
            if (resultCode == RESULT_OK) {
                isExpired = false
//                userTokenCache.get()?.userID?.let { userID = it }
                Handler().postDelayed({
                    reloadData()
                }, 500)
            }
        }
    }

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

    fun copyTextToClipboard(text: String) {
        val clipboardManager =
            activity?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("text", text)
        clipboardManager.setPrimaryClip(clipData)
        Toast.makeText(activity, " Đã sao chép vào bộ nhớ tạm", Toast.LENGTH_SHORT).show()
    }

    fun showDialogAnnouncement(message: String) {
        context?.let {
            DialogAlert()
                .setTitle(it.resources.getString(R.string.announcement))
                .setMessage(message)
                .setCancel(false)
                .setTitlePositive(it.getString(R.string.btn_ok))
                .show(it)
        }
    }


    fun setUpToolbar(toolbar: Toolbar, title: String?, listener: View.OnClickListener?) {
        if (activity is BaseActivity) {
            (activity as BaseActivity).setUpToolbar(toolbar, title, listener)
        }
    }

//    fun setUpColorSrl(srl: SwipeRefreshLayout) {
//        srl.setColorSchemeResources(
//            R.color.colorPrimary,
//            R.color.colorPrimary,
//            R.color.colorAccent
//        )
//    }

    fun showMessage(title: String? = null, message: String) {
        DialogAlert()
            .setTitle(if (!TextUtils.isEmpty(title)) title!! else getString(R.string.announcement))
            .setMessage(message)
            .setCancel(false)
            .setTitlePositive("OK")
            .show(requireContext())
    }

    fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun checkAcceptRuntimePermission(
        permission: String,
        permissionList: Array<String>,
        requestCode: Int
    ): Boolean {
        activity?.let {
            val permissionCheck = ActivityCompat.checkSelfPermission(
                it,
                permission
            )
            return if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(permissionList, requestCode)
                false
            } else {
                true
            }
        }
        return false
    }

    fun getImageDrawable(id: Int): Drawable? {
        context?.let {
            return ContextCompat.getDrawable(it, id)
        }
        return null
    }

    fun getColor(id: Int): Int {
        context?.let {
            return ContextCompat.getColor(it, id)
        }
        return Color.TRANSPARENT
    }

    fun finish(){
        activity?.finish()
    }

    fun showImagePicker() {
        ImagePicker.with(this)
            .galleryMimeTypes(  //Exclude gif images
                mimeTypes = arrayOf(
                    "image/png",
                    "image/jpg",
                    "image/jpeg"
                )
            )
            .start()
    }

    fun getImageSize(chosen: Uri?): Long {
        if (chosen == null) {
            return 0
        }
        val fileStr: String? = FileUriUtils.getRealPath(application.applicationContext, chosen)
        val file = File(fileStr)
        val fileSizeInBytes: Long = file.length()
        val fileSizeInKB = fileSizeInBytes / 1024
        return fileSizeInKB / 1024
    }
}
