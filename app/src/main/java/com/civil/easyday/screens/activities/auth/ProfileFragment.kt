package com.civil.easyday.screens.activities.auth

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.request.RequestOptions
import com.civil.easyday.R
import com.civil.easyday.screens.base.BaseActivity
import com.civil.easyday.screens.base.BaseActivity.Companion.profileLogoListener
import com.civil.easyday.screens.base.BaseFragment
import com.civil.easyday.utils.FileUtil
import com.civil.easyday.utils.IntentUtil
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageActivity
import com.theartofdev.edmodo.cropper.CropImageOptions
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : BaseFragment<ProfileViewModel>(), BaseActivity.OnProfileLogoChangeListener {

    override fun getContentView() = R.layout.fragment_profile

    override fun initUi() {

        camera.setOnClickListener {
            profileLogoListener = this
            if ((ActivityCompat.checkSelfPermission(
                    requireActivity(),
                    Manifest.permission.CAMERA
                ) != PackageManager.PERMISSION_GRANTED) || (ActivityCompat.checkSelfPermission(
                    requireActivity(),
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED) || (ActivityCompat.checkSelfPermission(
                    requireActivity(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED)
            ) {
                onPermission()
            } else {

                if (permission()) {
                    openIntent()
                }
            }
        }
    }

    private fun onPermission() {

        Dexter.withContext(requireContext())
            .withPermissions(Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(p0: MultiplePermissionsReport?) {
                    if(p0?.areAllPermissionsGranted() == true)
                        openIntent()
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: MutableList<PermissionRequest>?,
                    p1: PermissionToken?
                ) {
                }

            }).withErrorListener {}

            .check()
    }

    private fun permission(): Boolean {

        return ActivityCompat.checkSelfPermission(
            requireActivity(),
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            requireActivity(),
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            requireActivity(),
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED

    }

    private fun openIntent() {
        requireActivity().startActivityForResult(
            IntentUtil.getPickImageChooserIntent(requireContext(), requireActivity()),
            IntentUtil.PICK_IMAGE_CHOOSER_REQUEST_CODE
        )
    }

    override fun setObservers() {
    }

    override fun onCropLogo(uri: Uri) {
        val mOptions = CropImageOptions()
        mOptions.allowFlipping = false
        mOptions.allowRotation = false
        mOptions.aspectRatioX = 1
        mOptions.aspectRatioY = 1
        mOptions.cropShape = CropImageView.CropShape.OVAL
        val intent = Intent()
        intent.setClass(requireContext(), CropImageActivity::class.java)
        val bundle = Bundle()
        bundle.putParcelable(CropImage.CROP_IMAGE_EXTRA_SOURCE, uri)
        bundle.putParcelable(CropImage.CROP_IMAGE_EXTRA_OPTIONS, mOptions)
        intent.putExtra(CropImage.CROP_IMAGE_EXTRA_BUNDLE, bundle)
        requireActivity().startActivityForResult(
            intent,
            CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE
        )
    }

    override fun onChangeLogo(uri: Uri) {
        val selectedFile = uri.let {
            FileUtil.getPath(it, requireContext())
        }
        if (selectedFile != null) {
            val options = RequestOptions()
            avatar.clipToOutline = true
            Glide.with(requireContext())
                .load(uri)
                .apply(
                    options.centerCrop()
                        .skipMemoryCache(true)
                        .priority(Priority.HIGH)
                        .format(DecodeFormat.PREFER_ARGB_8888)
                )
                .into(avatar)
        }
    }
}