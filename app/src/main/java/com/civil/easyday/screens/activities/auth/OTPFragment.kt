package com.civil.easyday.screens.activities.auth

import android.text.Html
import androidx.navigation.Navigation
import com.civil.easyday.R
import com.civil.easyday.screens.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_o_t_p.*

class OTPFragment : BaseFragment<OTPViewModel>() {

    private var mPhoneNumber: String? = null

    override fun getContentView() = R.layout.fragment_o_t_p

    override fun initUi() {
        mPhoneNumber = arguments?.getString("phoneNumber")

        phoneNumber.text = Html.fromHtml("<u>$mPhoneNumber</u>")

        back.setOnClickListener {
            Navigation.findNavController(requireView()).popBackStack()
        }

        change.setOnClickListener {
            Navigation.findNavController(requireView()).popBackStack()
        }
    }

    override fun setObservers() {
    }
}