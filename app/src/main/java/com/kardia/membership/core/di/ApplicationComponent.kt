package com.kardia.membership.core.di

import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kardia.membership.AndroidApplication
import com.kardia.membership.core.di.viewmodel.ViewModelModule
import com.kardia.membership.core.navigation.RouteActivity
import com.kardia.membership.core.platform.BaseActivity
import com.kardia.membership.core.platform.BaseFragment
import com.kardia.membership.features.MainActivity
import com.kardia.membership.features.dialog.NoInternetDialog
import com.kardia.membership.features.fragments.buy.BuyFragment
import com.kardia.membership.features.fragments.check_mail.CheckMailFragment
import com.kardia.membership.features.fragments.confirm_passcode.ConfirmPasscodeFragment
import com.kardia.membership.features.fragments.create_passcode.CreatePasscodeFragment
import com.kardia.membership.features.fragments.enter_passcode.EnterPasscodeFragment
import com.kardia.membership.features.fragments.forgot_password.ForgotPasswordFragment
import com.kardia.membership.features.fragments.introduce.IntroduceFragment
import com.kardia.membership.features.fragments.introduce.IntroduceManagementFragment
import com.kardia.membership.features.fragments.kai_starter.KAIStarterFragment
import com.kardia.membership.features.fragments.login.LoginFragment
import com.kardia.membership.features.fragments.mission.MissionFragment
import com.kardia.membership.features.fragments.mission.SubMissionFragment
import com.kardia.membership.features.fragments.my_profile.MyProfileFragment
import com.kardia.membership.features.fragments.my_reward.MyRewardFragment
import com.kardia.membership.features.fragments.new_passcode.ChangePasswordSuccessBottomSheet
import com.kardia.membership.features.fragments.new_passcode.NewPasscodeFragment
import com.kardia.membership.features.fragments.new_password.NewPasswordFragment
import com.kardia.membership.features.fragments.new_password_success.NewPasswordSuccessFragment
import com.kardia.membership.features.fragments.news.NewsFragment
import com.kardia.membership.features.fragments.top_up_overview.ClaimTopUpFailBottomSheet
import com.kardia.membership.features.fragments.top_up_overview.ClaimTopUpSuccessBottomSheet
import com.kardia.membership.features.fragments.top_up_overview.TopUpOverviewFragment
import com.kardia.membership.features.fragments.profile.ProfileFragment
import com.kardia.membership.features.fragments.receive.ReceiveFragment
import com.kardia.membership.features.fragments.register.RegisterFragment
import com.kardia.membership.features.fragments.register_success.RegisterSuccessFragment
import com.kardia.membership.features.fragments.reset_passcode.ResetPasscodeFragment
import com.kardia.membership.features.fragments.reset_passcode_success.ResetPasscodeSuccessFragment
import com.kardia.membership.features.fragments.select_account.SelectAccountFragment
import com.kardia.membership.features.fragments.send.SendFragment
import com.kardia.membership.features.fragments.send_overview.SendOverviewFragment
import com.kardia.membership.features.fragments.splash.SplashFragment
import com.kardia.membership.features.fragments.top_up.TopUpAmountBottomSheet
import com.kardia.membership.features.fragments.top_up.TopUpFragment
import com.kardia.membership.features.fragments.utilities.UtilitiesFragment
import com.kardia.membership.features.fragments.verification.VerificationFragment
import com.kardia.membership.features.fragments.wallet.WalletFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, ViewModelModule::class])
interface ApplicationComponent {
    fun inject(application: AndroidApplication)
    fun inject(routeActivity: RouteActivity)
    fun inject(mainActivity: MainActivity)
    fun inject(baseActivity: BaseActivity)
    fun inject(baseFragment: BaseFragment)
    fun inject(bottomSheetDialogFragment: BottomSheetDialogFragment)
    fun inject(noInternetDialog: NoInternetDialog)
    fun inject(splashFragment: SplashFragment)
    fun inject(introduceManagementFragment: IntroduceManagementFragment)
    fun inject(introduceFragment: IntroduceFragment)
    fun inject(loginFragment: LoginFragment)
    fun inject(registerFragment: RegisterFragment)
    fun inject(createPasscodeFragment: CreatePasscodeFragment)
    fun inject(confirmPasscodeFragment: ConfirmPasscodeFragment)
    fun inject(registerSuccessFragment: RegisterSuccessFragment)
    fun inject(forgotPasswordFragment: ForgotPasswordFragment)
    fun inject(newPasswordFragment: NewPasswordFragment)
    fun inject(newPasswordSuccessFragment: NewPasswordSuccessFragment)
    fun inject(selectAccountFragment: SelectAccountFragment)
    fun inject(enterPasscodeFragment: EnterPasscodeFragment)
    fun inject(resetPasscodeFragment: ResetPasscodeFragment)
    fun inject(checkMailFragment: CheckMailFragment)
    fun inject(newPasscodeFragment: NewPasscodeFragment)
    fun inject(walletFragment: WalletFragment)
    fun inject(newsFragment: NewsFragment)
    fun inject(receiveFragment: ReceiveFragment)
    fun inject(buyFragment: BuyFragment)
    fun inject(utilitiesFragment: UtilitiesFragment)
    fun inject(topUpFragment: TopUpFragment)
    fun inject(overviewFragment: TopUpOverviewFragment)
    fun inject(topUpAmountBottomSheet: TopUpAmountBottomSheet)
    fun inject(claimTopUpFailBottomSheet: ClaimTopUpFailBottomSheet)
    fun inject(claimTopUpSuccessBottomSheet: ClaimTopUpSuccessBottomSheet)
    fun inject(profileFragment: ProfileFragment)
    fun inject(myProfileFragment: MyProfileFragment)
    fun inject(changePasswordSuccessBottomSheet: ChangePasswordSuccessBottomSheet)
    fun inject(missionFragment: MissionFragment)
    fun inject(subMissionFragment: SubMissionFragment)
    fun inject(myRewardFragment: MyRewardFragment)
    fun inject(verificationFragment: VerificationFragment)
    fun inject(resetPasscodeSuccessFragment: ResetPasscodeSuccessFragment)
    fun inject(sendFragment: SendFragment)
    fun inject(sendOverviewFragment: SendOverviewFragment)
    fun inject(kaiStarterFragment: KAIStarterFragment)
}
