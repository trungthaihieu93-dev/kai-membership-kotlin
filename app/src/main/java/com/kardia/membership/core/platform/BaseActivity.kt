package com.kardia.membership.core.platform

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Rect
import android.os.Bundle
import android.os.Handler
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.kardia.membership.AndroidApplication
import com.kardia.membership.R
import com.kardia.membership.R.layout
import com.kardia.membership.core.di.ApplicationComponent
import com.kardia.membership.core.navigation.Navigator
import com.kardia.membership.data.cache.UserInfoCache
import com.kardia.membership.data.cache.UserTokenCache
import com.kardia.membership.data.entities.UserInfo
import com.kardia.membership.features.dialog.DialogProgress
import kotlinx.android.synthetic.main.include_toolbar_white.view.*
import pl.droidsonroids.gif.GifDrawable
import javax.inject.Inject

/**
 * Base Activity class with helper methods for handling fragment transactions and back button
 * events.
 *
 * @see AppCompatActivity
 */
abstract class BaseActivity : AppCompatActivity() {
    var disableDispatchTouchEvent: Boolean = false
    var isTouchDisable: Boolean = false
    var isShowProgress = false
    var isShowNoInternet = false
    var isShowError = false
    var isOfflineMode = false
    var isHideKeyboardWhenClick = true
    var isUserLogin :Boolean = false
    lateinit var gifDrawable: GifDrawable

    companion object {
        var apiRequestCount = 0
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var userInfoCache: UserInfoCache

    @Inject
    lateinit var userTokenCache: UserTokenCache

    @Inject
    internal lateinit var mNavigator: Navigator

    val appComponent: ApplicationComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        (application as AndroidApplication).appComponent
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        (application as AndroidApplication).addThisActivityToRunningActivityies(this.javaClass)
        gifDrawable = GifDrawable(resources, R.drawable.loading_animation)
        setContentView(layout.activity_layout)
        addFragment()
        isHideKeyboardWhenClick = true
        userTokenCache.get()?.let{
            isUserLogin = true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        (application as AndroidApplication).removeThisActivityFromRunningActivities(this.javaClass)
        gifDrawable.recycle()
        apiRequestCount = 0
    }

    override fun onBackPressed() {
        if (!(getCurrentFragment()).onBackPressed())
            super.onBackPressed()
    }

    private fun addFragment() =
        fragment()?.let {
            BaseFragment.addFragmentByActivity(this, it)
        }

    abstract fun fragment(): BaseFragment?

    fun showProgress() {
        if (!isShowProgress) {
            isShowProgress = true
            DialogProgress(gifDrawable).show(this)
        }
    }

    fun forceShowProgress(){
        isShowProgress = true
        DialogProgress(gifDrawable).show(this)
    }

    fun hideProgress(delayTime: Int = 0) {
        Handler().postDelayed({
            if (apiRequestCount <= 0) {
                apiRequestCount = 0
                isShowProgress = false
                DialogProgress.hide(this)
            }
        }, delayTime.toLong())
    }

    fun forceHide() {
        apiRequestCount = 0
        isShowProgress = false
        DialogProgress.hide(this)
    }

    fun changeColorStatusBar(color: Int = R.color.color_background_app) {
        val window: Window = window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, color)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN && !disableDispatchTouchEvent) {
            val view = currentFocus
            if (view != null && (view is AppCompatEditText || view is EditText)) {
                val outRect = Rect()
                view.getGlobalVisibleRect(outRect)
                if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt()) && isHideKeyboardWhenClick) {
                    val imm = (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                    imm.hideSoftInputFromWindow(view.windowToken, 0)
                }
            }
        }
        return isTouchDisable || super.dispatchTouchEvent(event)
    }

    fun changeFullScreenMode(isLightTheme: Boolean = true) {
        if (isLightTheme)
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        else {
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }

        changeStatusBarColor(Color.TRANSPARENT)
    }


    fun unchangeFullScreenMode() {
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_VISIBLE or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }

    fun changeStatusBarColor(color: Int) {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = color
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            val fragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)
            if (fragment is BaseFragment && fragment.onBackPressed()) {
                return true
            }
            if (!defaultBack()) {
                return super.onKeyDown(keyCode, event)
            }
            return true
        }

        return super.onKeyDown(keyCode, event)
    }


    fun getCurrentFragmentLevel(): Int {
        val currentFragment =
            supportFragmentManager?.findFragmentById(R.id.fragmentContainer) as BaseFragment
        return currentFragment.getFragmentLevel()
    }

    fun getCurrentFragmentName(): String {
        val currentFragment =
            supportFragmentManager?.findFragmentById(R.id.fragmentContainer) as BaseFragment
        return currentFragment.javaClass.simpleName
    }

    fun getCurrentFragment(): BaseFragment {
        return supportFragmentManager.findFragmentById(R.id.fragmentContainer) as BaseFragment
    }

    fun handleBack() {
        //finish when stack only have 1 item
        if (supportFragmentManager.backStackEntryCount <= 1) {
            finish()
            return
        }

        //back to previous Fragment
        if (!(getCurrentFragment()).onBackPressed()) {
            super.onBackPressed()
        }
    }

    fun defaultBack(): Boolean {
        if (supportFragmentManager.backStackEntryCount <= 1) {
            finish()
            return true
        }
        return false
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun setUpToolbar(toolbar: Toolbar, title: String?, listener: View.OnClickListener?) {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        title?.let {
            toolbar.tv_header.text = it
        }
        listener?.let {
            toolbar.setNavigationOnClickListener(listener)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        for (fragment in supportFragmentManager.fragments) {
            fragment.onActivityResult(requestCode, resultCode, data)
        }
    }
}

