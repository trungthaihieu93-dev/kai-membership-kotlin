package com.kardia.membership.core.di.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kardia.membership.features.viewmodel.*
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    //declare view model here
    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindsMainViewModelViewModel(mainViewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DeviceViewModel::class)
    abstract fun bindsDeviceViewModel(viewModel: DeviceViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel::class)
    abstract fun bindsAuthViewModel(viewModel: AuthViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PasscodeViewModel::class)
    abstract fun bindsPasscodeViewModel(viewModel: PasscodeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UserViewModel::class)
    abstract fun bindsUserViewModel(viewModel: UserViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TransactionViewModel::class)
    abstract fun bindsTransactionViewModel(viewModel: TransactionViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ConfigViewModel::class)
    abstract fun bindsConfigViewModel(viewModel: ConfigViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TopUpViewModel::class)
    abstract fun bindsTopUpViewModel(viewModel: TopUpViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(QuestViewModel::class)
    abstract fun bindsQuestViewModel(viewModel: QuestViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TrackingViewModel::class)
    abstract fun bindsTrackingViewModel(viewModel: TrackingViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CaptchaViewModel::class)
    abstract fun bindsCaptchaViewModel(viewModel: CaptchaViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(NewsViewModel::class)
    abstract fun bindsNewsViewModel(viewModel: NewsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(WalletViewModel::class)
    abstract fun bindsWalletViewModel(viewModel: WalletViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ReferralViewModel::class)
    abstract fun bindsReferralViewModel(viewModel: ReferralViewModel): ViewModel
}