package com.civil.easyday.screens.activities.auth

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.request.RequestOptions
import com.civil.easyday.R
import com.civil.easyday.app.sources.local.prefrences.AppPreferencesDelegates
import com.civil.easyday.screens.activities.main.MainActivity
import com.civil.easyday.screens.base.BaseActivity
import com.civil.easyday.screens.base.BaseActivity.Companion.profileLogoListener
import com.civil.easyday.screens.base.BaseFragment
import com.civil.easyday.utils.FileUtil
import com.civil.easyday.utils.IntentUtil
import com.google.android.material.textfield.TextInputEditText
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageActivity
import com.theartofdev.edmodo.cropper.CropImageOptions
import com.theartofdev.edmodo.cropper.CropImageView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_profile.*


@AndroidEntryPoint
class ProfileFragment : BaseFragment<ProfileViewModel>(), BaseActivity.OnProfileLogoChangeListener {

    override fun getContentView() = R.layout.fragment_profile
    var isNewUser: Boolean? = null

    override fun getStatusBarColor()=ContextCompat.getColor(requireContext(), R.color.bg_white)

    override fun initUi() {

        val mPhoneNumber = arguments?.getString("phoneNumber")
        isNewUser = arguments?.getBoolean("isNewUser", true)
        if (isNewUser == false) {
            viewModel.getProfile()
        }

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

        cta.setOnClickListener {
            if (isNewUser == true) {
                if (fullName.text.isNullOrEmpty()) {
                    Toast.makeText(
                        requireContext(),
                        requireContext().resources.getString(R.string.name_constarin),
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }

                if (profession.text.isNullOrEmpty()) {
                    Toast.makeText(
                        requireContext(),
                        requireContext().resources.getString(R.string.profession_constarin),
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }

                if (mPhoneNumber != null) {
                    viewModel.createUser(
                        fullName.text.toString(),
                        profession.text.toString(),
                        mPhoneNumber
                    )
                }
            } else {

                //update API
                val intent = Intent (requireActivity(), MainActivity::class.java)
                requireActivity().startActivity(intent)
            }
        }

        fullName.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                if (s.isNotEmpty()) {
                    setTextViewDrawableColor(fullName, R.color.green)
                } else {
                    setTextViewDrawableColor(fullName, R.color.gray)
                }
                checkData()
            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {

            }
        })

        profession.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                if (s.isNotEmpty()) {
                    setTextViewDrawableColor(profession, R.color.green)
                } else {
                    setTextViewDrawableColor(profession, R.color.gray)
                }
                checkData()
            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {

            }
        })

        requireView().isFocusableInTouchMode = true
        requireView().requestFocus()
        requireView().setOnKeyListener { v, keyCode, event ->
            event.action == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK
        }
    }

    private fun setTextViewDrawableColor(editText: TextInputEditText, color: Int) {
        for (drawable in editText.compoundDrawables) {
            if (drawable != null) {
                drawable.colorFilter =
                    PorterDuffColorFilter(
                        ContextCompat.getColor(editText.context, color),
                        PorterDuff.Mode.SRC_IN
                    )
            }
        }
    }

    fun checkData() {
        if (!fullName.text.isNullOrEmpty() && !profession.text.isNullOrEmpty()) {
            cta.isEnabled = true
            cta.alpha = 1F
        } else {
            cta.isEnabled = false
            cta.alpha = 0.5F
        }
    }

    private fun onPermission() {

        Dexter.withContext(requireContext())
            .withPermissions(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(p0: MultiplePermissionsReport?) {
                    if (p0?.areAllPermissionsGranted() == true)
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
        viewModel.userData.observe(viewLifecycleOwner) { userData ->
            fullName.setText(userData?.fullname)
            profession.setText(userData?.profession)

            if (userData?.profileImage != null) {
                val options = RequestOptions()
                avatar.clipToOutline = true
                Glide.with(requireContext())
                    .load(userData.profileImage)
                    .apply(
                        options.centerCrop()
                            .skipMemoryCache(true)
                            .priority(Priority.HIGH)
                            .format(DecodeFormat.PREFER_ARGB_8888)
                    )
                    .into(avatar)
            }

            if (isNewUser == true)
                AppPreferencesDelegates.get().token = userData?.token.toString()
        }

        viewModel.actionStream.observe(viewLifecycleOwner){
            when(it){
                is ProfileViewModel.ACTION.onAddUpdateUser->{
                    val intent = Intent (requireActivity(), MainActivity::class.java)
                    requireActivity().startActivity(intent)
                }
            }
        }
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