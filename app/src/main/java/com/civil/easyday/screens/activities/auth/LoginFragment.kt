package com.civil.easyday.screens.activities.auth

import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.civil.easyday.R
import com.civil.easyday.app.sources.local.model.PhoneCodeModel
import com.civil.easyday.screens.base.BaseFragment
import com.civil.easyday.screens.dialogs.CodeDialog
import com.civil.easyday.utils.CountryCityUtils
import com.civil.easyday.utils.DeviceUtils
import com.civil.easyday.utils.FileUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_login.*
import org.json.JSONObject
import java.util.*


@AndroidEntryPoint
class LoginFragment : BaseFragment<LoginViewModel>(), CodeDialog.CountyPickerItems {

    override fun getContentView() = R.layout.fragment_login
    private var countryCode: String? = null


    override fun initUi() {

        setPhoneCountryData()

        cta.setOnClickListener {

            DeviceUtils.hideKeyboard(requireContext())
            if (phone.text?.isNotEmpty() == true && countryCode?.isNotEmpty() == true) {
                val fullNumber=countryCode + phone.text
                val action = LoginFragmentDirections.loginToOtp()
                action.phoneNumber=fullNumber
                val nav: NavController = Navigation.findNavController(cta)
                if (nav.currentDestination != null && nav.currentDestination?.id == R.id.loginFragment) {
                    nav.navigate(action)
                }
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

        if (obj != null) {
            for (key in obj.keys()) {
                val keyStr = key as String
                val keyValue = obj[keyStr]
                val code = PhoneCodeModel(keyStr, keyValue as String?)
                phoneModelList.add(code)
            }
        }

        phoneCode.text = "+ ${phoneModelList[0].value}"
        countryCode = phoneCode.text.toString()

        phoneFlag.text =
            CountryCityUtils.getFlagId(
                CountryCityUtils.firstTwo(
                    phoneModelList[0].key?.lowercase(Locale.getDefault()).toString()
                )
            )

        val phoneCodeDialog = CodeDialog(requireActivity(), phoneModelList, this)

        cc_dialog.setOnClickListener {
            phoneCodeDialog.show(requireActivity().supportFragmentManager, null)
        }
    }

    override fun setObservers() {
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