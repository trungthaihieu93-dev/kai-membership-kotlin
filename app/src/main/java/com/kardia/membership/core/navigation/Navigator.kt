package com.kardia.membership.core.navigation

import android.app.Activity
import androidx.fragment.app.FragmentActivity
import com.kardia.membership.core.platform.BaseFragment
import com.kardia.membership.features.activities.*
import com.kardia.membership.features.fragments.check_mail.CheckMailFragment
import com.kardia.membership.features.fragments.confirm_passcode.ConfirmPasscodeFragment
import com.kardia.membership.features.fragments.enter_passcode.EnterPasscodeFragment
import com.kardia.membership.features.fragments.introduce.IntroduceManagementFragment
import com.kardia.membership.features.fragments.new_passcode.NewPasscodeFragment
import com.kardia.membership.features.fragments.new_password.NewPasswordFragment
import com.kardia.membership.features.fragments.register_success.RegisterSuccessFragment
import com.kardia.membership.features.fragments.reset_passcode.ResetPasscodeFragment
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class Navigator
@Inject constructor() {
    fun showMain(context: Activity?) {

    }

    fun showSplash(context: Activity?) =
        context?.startActivity(SplashActivity.callingIntent(context))

    fun showIntroduce(activity: FragmentActivity?) = activity?.startActivity(
        IntroduceManagementActivity.callingIntent(activity).apply { }
    )

    fun showLogin(activity: FragmentActivity?) = activity?.startActivity(
        LoginActivity.callingIntent(activity).apply { }
    )

    fun showRegister(activity: FragmentActivity?) = activity?.startActivity(
        RegisterActivity.callingIntent(activity).apply { }
    )

    fun showCreatePasscode(activity: FragmentActivity?) = activity?.startActivity(
        CreatePasscodeActivity.callingIntent(activity).apply { }
    )

    fun showConfirmPasscode(activity: FragmentActivity?) {
        BaseFragment.addFragmentByActivity(activity, ConfirmPasscodeFragment())
    }

    fun showRegisterSuccess(activity: FragmentActivity?) {
        BaseFragment.addFragmentByActivity(activity, RegisterSuccessFragment())
    }

    fun showForgotPassword(activity: FragmentActivity?) = activity?.startActivity(
        ForgotPasswordActivity.callingIntent(activity).apply { }
    )

    fun showNewPassword(activity: FragmentActivity?) {
        BaseFragment.addFragmentByActivity(activity, NewPasswordFragment())
    }

    fun showNewPasswordSuccess(activity: FragmentActivity?) = activity?.startActivity(
        NewPasswordSuccessActivity.callingIntent(activity).apply { }
    )

    fun showSelectAccount(activity: FragmentActivity?) = activity?.startActivity(
        SelectAccountActivity.callingIntent(activity).apply { }
    )

    fun showEnterPasscode(activity: FragmentActivity?){
        BaseFragment.addFragmentByActivity(activity, EnterPasscodeFragment())
    }

    fun showResetPasscode(activity: FragmentActivity?){
        BaseFragment.addFragmentByActivity(activity, ResetPasscodeFragment())
    }
    fun showCheckMail(activity: FragmentActivity?){
        BaseFragment.addFragmentByActivity(activity, CheckMailFragment())
    }

    fun showNewPasscode(activity: FragmentActivity?){
        BaseFragment.addFragmentByActivity(activity, NewPasscodeFragment())
    }
}