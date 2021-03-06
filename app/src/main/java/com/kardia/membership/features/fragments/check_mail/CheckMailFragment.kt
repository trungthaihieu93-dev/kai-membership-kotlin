package com.kardia.membership.features.fragments.check_mail

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import androidx.core.text.color
import androidx.core.text.set
import com.kardia.membership.R
import com.kardia.membership.core.extension.observe
import com.kardia.membership.core.extension.viewModel
import com.kardia.membership.core.platform.BaseFragment
import com.kardia.membership.domain.entities.passcode.ForgotPasscodeEntity
import com.kardia.membership.domain.entities.passcode.VerifyPasscodeEntity
import com.kardia.membership.domain.usecases.passcode.PostForgotPasscodeUseCase
import com.kardia.membership.domain.usecases.passcode.PostVerifyPasscodeUseCase
import com.kardia.membership.features.utils.AppConstants
import com.kardia.membership.features.viewmodel.PasscodeViewModel
import kotlinx.android.synthetic.main.fragment_check_mail.*


class CheckMailFragment : BaseFragment() {
    private lateinit var passcodeViewModel: PasscodeViewModel

    private var email: String? = null
    private var fromMission: Boolean = false

    companion object {
        const val EMAIL = "email"
        const val FROM_MISSION = "fromMission"
    }

    override fun layoutId() = R.layout.fragment_check_mail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        passcodeViewModel = viewModel(viewModelFactory) {
            observe(forgotEntity, ::onReceiveForgotPasscodeEntity)
            observe(verifyEntity, ::onReceiveVerifyEmailEntity)
            observe(failureData, ::handleFailure)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        email = arguments?.getString(EMAIL)
        arguments?.getBoolean(FROM_MISSION, false)?.let {
            fromMission = it
        }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun initViews() {
        isNotCatch401 = true
        email?.let { email ->
            val myCustomizedString = SpannableStringBuilder()
                .append(getString(R.string.content_instruction_sent_1))
                .append(" ")
                .color(getColor(R.color.color_017CAD)) {
                    append(email)
                }
                .append(" ")
                .append(getString(R.string.content_instruction_sent_2))
            tvContentInstructionSent.text = myCustomizedString
        }

        val myCustomizedString = SpannableStringBuilder()
            .append(getString(R.string.content_not_receive_any_email))
            .append(" ")
            .color(getColor(R.color.color_94A2B2)) {
                append(getString(R.string.resend_another_mail))
            }
        myCustomizedString[getString(R.string.content_not_receive_any_email).length + 1 until getString(
            R.string.content_not_receive_any_email
        ).length + 1 + getString(R.string.resend_another_mail).length + 1] =
            object : ClickableSpan() {
                override fun onClick(view: View) {
                    email?.let { email ->
                        showProgress()
                        if (!fromMission) {
                            passcodeViewModel.forgotPasscode(
                                PostForgotPasscodeUseCase.Params(
                                    email,
                                    AppConstants.DEVICE_ID
                                )
                            )
                        } else {
                            passcodeViewModel.verifyPasscode(PostVerifyPasscodeUseCase.Params(email))
                        }
                    }
                }

                override fun updateDrawState(ds: TextPaint) { // override updateDrawState
                    ds.isUnderlineText = false // set to false to remove underline
                }
            }
        tvReSendEmail.highlightColor = Color.TRANSPARENT;
        tvReSendEmail.movementMethod = LinkMovementMethod()
        tvReSendEmail.text = myCustomizedString
    }

    override fun initEvents() {
        btOpenEmailApp.setOnClickListener {
            if (!fromMission) {
                mNavigator.showVerification(activity, email)
            } else {
                activity?.let { activity ->
                    val intent = Intent(Intent.ACTION_MAIN)
                    intent.addCategory(Intent.CATEGORY_APP_EMAIL)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    activity.startActivity(intent)
                }
            }
        }
    }

    override fun loadData() {
    }

    override fun reloadData() {

    }

    private fun onReceiveForgotPasscodeEntity(entity: ForgotPasscodeEntity?) {
        hideProgress()
        showToast("Sent")
    }

    private fun onReceiveVerifyEmailEntity(entity: VerifyPasscodeEntity?) {
        hideProgress()
    }
}