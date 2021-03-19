package com.kardia.membership.features.fragments.profile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.github.dhaval2404.imagepicker.ImagePicker
import com.kardia.membership.R
import com.kardia.membership.core.extension.*
import com.kardia.membership.core.platform.BaseFragment
import com.kardia.membership.domain.entities.user.UpdateAvatarEntity
import com.kardia.membership.domain.entities.user.UserInfoEntity
import com.kardia.membership.features.utils.CommonUtils.getMimeType
import com.kardia.membership.features.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File


class ProfileFragment : BaseFragment() {
    private lateinit var userViewModel: UserViewModel

    override fun layoutId() = R.layout.fragment_profile

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        userViewModel = viewModel(viewModelFactory) {
            observe(getUserInfoEntity, ::onReceiveUserInfoEntity)
            observe(updateAvatarEntity, ::onReceiveUpdateAvatarEntity)
            observe(failureData, ::handleFailure)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun initViews() {
        ivBack.visible()
        ivCamera.visible()
        flPaddingTop.visible()
    }

    override fun initEvents() {
        ivBack.setOnClickListener {
            finish()
        }

        btShareIdUser.setOnClickListener {
            val share = Intent(Intent.ACTION_SEND)
            share.type = "text/plain"
            share.putExtra(Intent.EXTRA_TEXT, userInfoCache.get()?.user_info?._id)
            startActivity(Intent.createChooser(share, "Share Text"))
        }

        mcvMyProfile.setOnClickListener {
            mNavigator.showMyProfile(activity)
        }

        mcvMyRewards.setOnClickListener {
            mNavigator.showMyReward(activity)
        }

        mcvChangePassword.setOnClickListener {
            mNavigator.showEnterPasscode(
                activity,
                userInfoCache.get()?.kai_info?.email,
                isFromChangePassword = true
            )
        }

        mcvSwitchAccount.setOnClickListener {
            mNavigator.showSelectAccountNew(activity)
        }

        mcvSignOut.setOnClickListener {
            mNavigator.showLoginNew(activity)
        }

        ivCamera.setOnClickListener {
            showImagePicker()
        }
    }

    override fun loadData() {
        userInfoCache.get()?.let { userInfo ->
            ivAvatarProfile.loadFromUrl(userInfo.user_info?.avatar)
            tvIdProfile.text = userInfo.user_info?._id
            tvNameProfile.text = userInfo.kai_info?.first_name
            tvEmailProfile.text = userInfo.kai_info?.email
        }
    }

    override fun reloadData() {

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            ImagePicker.REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
//                    if (getImageSize(data?.data) > 10) {
//                        showMessage(null, getString(R.string.validate_size_of_image_message))
//                        return
//                    }
//                    val fileUri = data?.data
//
//                    ivAvatarProfile.loadFromAnyWithoutCache(
//                        fileUri
//                    )

                    //You can get File object from intent
                    val file = ImagePicker.getFile(data)

                    //You can also get File Path from intent
                    val filePath = ImagePicker.getFilePath(data)
                    val fileRequest =
                        file?.asRequestBody(getMimeType(filePath)?.toMediaTypeOrNull())
                    fileRequest?.let {
                        val fileToUpload: MultipartBody.Part =
                            MultipartBody.Part.createFormData("avatar", file.name, fileRequest)
                        showProgress()
                        userViewModel.updateAvatar(fileToUpload)
                    }
                } else if (resultCode == ImagePicker.RESULT_ERROR) {
                    Toast.makeText(activity, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun onReceiveUpdateAvatarEntity(entity: UpdateAvatarEntity?) {
        hideProgress()
        userViewModel.getUserInfo()
    }

    private fun onReceiveUserInfoEntity(entity: UserInfoEntity?) {
        forceHide()
        entity?.data?.let {
            userInfoCache.put(it)
            loadData()
        }
    }
}