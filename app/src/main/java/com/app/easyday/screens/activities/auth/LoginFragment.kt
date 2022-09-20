package com.app.easyday.screens.activities.auth

import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.app.easyday.R
import com.app.easyday.app.sources.local.model.PhoneCodeModel
import com.app.easyday.screens.base.BaseFragment
import com.app.easyday.screens.dialogs.CodeDialog
import com.app.easyday.utils.CountryCityUtils
import com.app.easyday.utils.DeviceUtils
import com.app.easyday.utils.FileUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_login.*
import org.json.JSONObject
import java.util.*


@AndroidEntryPoint
class LoginFragment : BaseFragment<LoginViewModel>(), CodeDialog.CountyPickerItems {

    override fun getContentView() = R.layout.fragment_login
    private var countryCode: String? = null
    private var trimCode = ""

    override fun getStatusBarColor() = ContextCompat.getColor(requireContext(), R.color.bg_white)

    override fun initUi() {
        DeviceUtils.initProgress(requireContext())

        setPhoneCountryData()
        cta.setOnClickListener {
            errorText.isVisible = false
            DeviceUtils.hideKeyboard(requireContext())
            if (phone.text?.isNotEmpty() == true && countryCode?.isNotEmpty() == true) {
                trimCode = countryCode?.replace("\\s+".toRegex(), "").toString()
                trimCode = trimCode.replace("+", "")
                Log.e("trimcode", trimCode)
                viewModel.sendOTP(phone.text.toString(), trimCode)
            } else {
                Toast.makeText(
                    requireContext(),
                    requireContext().resources.getString(R.string.number_requires),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun setPhoneCountryData() {
        val phoneModelList: ArrayList<PhoneCodeModel> = ArrayList()
        val obj = FileUtil.loadJSONFromAsset(requireContext())?.let { JSONObject(it) }

        var defaultCode: PhoneCodeModel? = null
        if (obj != null) {
            for (key in obj.keys()) {
                val keyStr = key as String
                val keyValue = obj[keyStr]
                val code = PhoneCodeModel(keyStr, keyValue as String?)
                phoneModelList.add(code)
                if (code.key == "IN") {
                    defaultCode = code
                }
            }
        }


        phoneCode.text = "+ ${defaultCode?.value}"
        countryCode = phoneCode.text.toString()

        phoneFlag.text =
            CountryCityUtils.getFlagId(
                CountryCityUtils.firstTwo(
                    defaultCode?.key?.lowercase(Locale.getDefault()).toString()
                )
            )

        val phoneCodeDialog = CodeDialog(requireActivity(), phoneModelList, this)

        cc_dialog.setOnClickListener {
            phoneCodeDialog.show(requireActivity().supportFragmentManager, null)
        }
    }

    override fun setObservers() {
        viewModel.actionStream.observe(viewLifecycleOwner) {
            when (it) {
                is LoginViewModel.ACTION.GetOTPMsg -> {
                    Toast.makeText(requireContext(), it.msg, Toast.LENGTH_SHORT).show()

                    val action = LoginFragmentDirections.loginToOtp()
                    action.phoneNumber = phone.text.toString()
                    action.countryCode = trimCode
                    val nav: NavController = Navigation.findNavController(requireView())
                    if (nav.currentDestination != null && nav.currentDestination?.id == R.id.loginFragment) {
                        nav.navigate(action)
                    }
                }
                is LoginViewModel.ACTION.GetErrorMsg -> {
                    errorText.text = it.msg
                    errorText.isVisible = true
                }
            }
        }
    }

    override fun pickCountry(countries: PhoneCodeModel) {
        phoneCode.text = "+ ${countries.value}"
        countryCode = phoneCode.text.toString()
        phoneFlag.text =
            CountryCityUtils.getFlagId(
                CountryCityUtils.firstTwo(
                    countries.key?.lowercase(Locale.getDefault()).toString()
                ).toString()
            )
    }
}