package com.kardia.membership.core.navigation

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.kardia.membership.features.MainActivity
import com.kardia.membership.core.platform.BaseFragment
import com.kardia.membership.data.entities.PasscodeDevice
import com.kardia.membership.data.entities.TopUpAmount
import com.kardia.membership.features.activities.*
import com.kardia.membership.features.fragments.check_mail.CheckMailFragment
import com.kardia.membership.features.fragments.confirm_passcode.ConfirmPasscodeFragment
import com.kardia.membership.features.fragments.enter_passcode.EnterPasscodeFragment
import com.kardia.membership.features.fragments.my_profile.MyProfileFragment
import com.kardia.membership.features.fragments.my_reward.MyRewardFragment
import com.kardia.membership.features.fragments.new_passcode.ChangePasswordSuccessBottomSheet
import com.kardia.membership.features.fragments.new_passcode.NewPasscodeFragment
import com.kardia.membership.features.fragments.new_password.NewPasswordFragment
import com.kardia.membership.features.fragments.overview.ClaimTopUpFailBottomSheet
import com.kardia.membership.features.fragments.overview.ClaimTopUpSuccessBottomSheet
import com.kardia.membership.features.fragments.overview.OverviewFragment
import com.kardia.membership.features.fragments.profile.ProfileFragment
import com.kardia.membership.features.fragments.register_success.RegisterSuccessFragment
import com.kardia.membership.features.fragments.reset_passcode.ResetPasscodeFragment
import com.kardia.membership.features.fragments.select_account.SelectAccountFragment
import com.kardia.membership.features.fragments.top_up.TopUpAmountBottomSheet
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class Navigator
@Inject constructor() {
    fun showMain(context: Activity?) = context?.startActivity(
        MainActivity.callingIntent(context).apply {
            addFlags(
                Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or
                        Intent.FLAG_ACTIVITY_CLEAR_TASK
            )
        })

    fun showMainWithTab(context: Activity?, tab: Int?) = context?.startActivity(
        MainActivity.callingIntent(context).apply {
            putExtra(MainActivity.TAB, tab)
            addFlags(
                Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or
                        Intent.FLAG_ACTIVITY_CLEAR_TASK
            )
        })

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

    fun showNewPassword(activity: FragmentActivity?, isFromChangePassword: Boolean = false) {
        BaseFragment.addFragmentByActivity(activity, NewPasswordFragment().apply {
            arguments = Bundle().apply {
                putBoolean(NewPasswordFragment.FROM_CHANGE_PASSWORD, isFromChangePassword)
            }
        })
    }

    fun showNewPasswordSuccess(activity: FragmentActivity?) = activity?.startActivity(
        NewPasswordSuccessActivity.callingIntent(activity).apply { }
    )

    fun showSelectAccount(activity: FragmentActivity?, passcodeDevice: PasscodeDevice?) =
        activity?.startActivity(
            SelectAccountActivity.callingIntent(activity).apply {
                putExtra(SelectAccountFragment.PASSCODE_DEVICE, passcodeDevice)
            }
        )

    fun showEnterPasscode(
        activity: FragmentActivity?,
        email: String? = null,
        isFromChangePassword: Boolean = false
    ) {
        BaseFragment.addFragmentByActivity(activity, EnterPasscodeFragment().apply {
            arguments = Bundle().apply {
                putString(EnterPasscodeFragment.EMAIL, email)
                putBoolean(EnterPasscodeFragment.FROM_CHANGE_PASSWORD, isFromChangePassword)
            }
        })
    }

    fun showResetPasscode(activity: FragmentActivity?, email: String?) {
        BaseFragment.addFragmentByActivity(activity, ResetPasscodeFragment().apply {
            arguments = Bundle().apply {
                putString(ResetPasscodeFragment.EMAIL, email)
            }
        })
    }

    fun showCheckMail(activity: FragmentActivity?, email: String?) {
        BaseFragment.addFragmentByActivity(activity, CheckMailFragment().apply {
            arguments = Bundle().apply {
                putString(CheckMailFragment.EMAIL, email)
            }
        })
    }

    fun showNewPasscode(activity: FragmentActivity?) {
        BaseFragment.addFragmentByActivity(activity, NewPasscodeFragment())
    }

    fun showReceive(activity: FragmentActivity?) = activity?.startActivity(
        ReceiveActivity.callingIntent(activity).apply { }
    )

    fun showBuy(activity: FragmentActivity?) = activity?.startActivity(
        BuyActivity.callingIntent(activity).apply { }
    )

    fun showSelectAccountNew(activity: FragmentActivity?) =
        activity?.startActivity(
            SelectAccountActivity.callingIntent(activity).apply {
                addFlags(
                    Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or
                            Intent.FLAG_ACTIVITY_CLEAR_TASK
                )
            }
        )

    fun showTopUp(activity: FragmentActivity?) = activity?.startActivity(
        TopUpActivity.callingIntent(activity).apply { }
    )

    fun showTopUpAmount(
        activity: FragmentActivity?,
        callback: TopUpAmountBottomSheet.CallBack
    ) {
        activity?.let {
            val bottomSheet = TopUpAmountBottomSheet()
            bottomSheet.setCallBack(callback)
            bottomSheet.show(
                it.supportFragmentManager,
                bottomSheet.tag
            )
        }
    }

    fun showOverview(
        activity: FragmentActivity?,
        phone: String,
        providerCode: String?,
        topUpAmount: TopUpAmount?
    ) {
        BaseFragment.addFragmentByActivity(activity, OverviewFragment().apply {
            arguments = Bundle().apply {
                putString(OverviewFragment.PHONE, phone)
                putString(OverviewFragment.PROVIDER_CODE, providerCode)
                putParcelable(OverviewFragment.TOP_UP_AMOUNT, topUpAmount)
            }
        })
    }

    fun showClaimTopUpFail(
        activity: FragmentActivity?,
        callback: ClaimTopUpFailBottomSheet.CallBack
    ) {
        activity?.let {
            val bottomSheet = ClaimTopUpFailBottomSheet()
            bottomSheet.setCallBack(callback)
            bottomSheet.show(
                it.supportFragmentManager,
                bottomSheet.tag
            )
        }
    }

    fun showClaimTopUpSuccess(
        activity: FragmentActivity?,
        callback: ClaimTopUpSuccessBottomSheet.CallBack
    ) {
        activity?.let {
            val bottomSheet = ClaimTopUpSuccessBottomSheet()
            bottomSheet.setCallBack(callback)
            bottomSheet.show(
                it.supportFragmentManager,
                bottomSheet.tag
            )
        }
    }

    fun showProfile(activity: FragmentActivity?) = activity?.startActivity(
        ProfileActivity.callingIntent(activity).apply { }
    )

    fun showMyProfile(activity: FragmentActivity?) {
        BaseFragment.addFragmentByActivity(activity, MyProfileFragment())
    }

    fun showLoginNew(activity: FragmentActivity?) =
        activity?.startActivity(
            LoginActivity.callingIntent(activity).apply {
                addFlags(
                    Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or
                            Intent.FLAG_ACTIVITY_CLEAR_TASK
                )
            }
        )

    fun showChangePasswordSuccess(
        activity: FragmentActivity?,
        callback: ChangePasswordSuccessBottomSheet.CallBack
    ) {
        activity?.let {
            val bottomSheet = ChangePasswordSuccessBottomSheet()
            bottomSheet.setCallBack(callback)
            bottomSheet.show(
                it.supportFragmentManager,
                bottomSheet.tag
            )
        }
    }

    fun showMyReward(activity: FragmentActivity?) {
        BaseFragment.addFragmentByActivity(activity, MyRewardFragment())
    }
}