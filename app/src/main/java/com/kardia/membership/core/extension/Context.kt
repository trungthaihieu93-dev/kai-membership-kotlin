
package com.kardia.membership.core.extension

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.view.inputmethod.InputMethodManager

val Context.networkInfo: NetworkInfo? get() =
    (this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo

fun Context.showKeyboard() {
    val imm = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
}

fun Context.hideKeyboard() {
    val imm = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.toggleSoftInput(0, InputMethodManager.HIDE_IMPLICIT_ONLY)
}

fun Context.toggleKeyboard() {
    val imm = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    if (imm.isActive) {
        hideKeyboard()
    } else {
        showKeyboard()
    }
}