package com.app.easyday.screens.activities.main.more.devices

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.app.easyday.R
import com.app.easyday.screens.activities.auth.LoginFragmentDirections
import com.app.easyday.screens.activities.main.more.profile.ViewProfileViewModel
import com.app.easyday.screens.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_devices.*
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.terms


class DevicesFragment : BaseFragment<DevicesViewModel>() {


    override fun getContentView() = R.layout.fragment_devices

    @SuppressLint("StringFormatInvalid")
    override fun initUi() {
        setHighLightedText(terms, requireContext().resources.getString(R.string.easy_day_desktop))
        setHighLightedText(terms, requireContext().resources.getString(R.string.easy_day_web))

        val dot = requireContext().resources.getString(R.string.dot)
        location?.text = requireContext().resources.getString(R.string.hyderabad, dot)
        location2?.text = requireContext().resources.getString(R.string.hyderabad, dot)
        location3?.text = requireContext().resources.getString(R.string.hyderabad, dot)
    }

    override fun setObservers() {

    }

    private fun setHighLightedText(tv: TextView, textToHighlight: String) {

        val tvt = tv.text.toString()
        var ofe = tvt.indexOf(textToHighlight, 0)
        val wordToSpan: Spannable = SpannableString(tv.text)
        var ofs = 0
        while (ofs < tvt.length && ofe != -1) {
            ofe = tvt.indexOf(textToHighlight, ofs)
            if (ofe == -1) break else {
                wordToSpan.setSpan(
                    object : ClickableSpan() {
                        override fun onClick(textView: View) {
                            val action = LoginFragmentDirections.loginToTerms()
                            if (textToHighlight == requireContext().resources.getString(R.string.terms))
                                action.url = "http://63.32.98.218/terms_of_service.html"
                            else
                                action.url = "http://63.32.98.218/privacy_policy.html"
                            action.title = textToHighlight
                            val nav: NavController = Navigation.findNavController(requireView())
                            if (nav.currentDestination != null && nav.currentDestination?.id == R.id.loginFragment) {
                                nav.navigate(action)
                            }
                        }

                        override fun updateDrawState(ds: TextPaint) {
                            super.updateDrawState(ds)
                            ds.isUnderlineText = false
                        }
                    },
                    ofe,
                    ofe + textToHighlight.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                tv.movementMethod = LinkMovementMethod.getInstance();
                tv.setText(wordToSpan, TextView.BufferType.SPANNABLE)
            }
            ofs = ofe + 1
        }
    }

}