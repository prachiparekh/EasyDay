package com.civil.easyday.screens.activities.auth

import android.os.CountDownTimer
import android.text.Html
import android.view.KeyEvent
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.civil.easyday.R
import com.civil.easyday.screens.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_o_t_p.*

class OTPFragment : BaseFragment<OTPViewModel>() {

    private var mPhoneNumber: String? = null

    override fun getContentView() = R.layout.fragment_o_t_p

    override fun initUi() {
        mPhoneNumber = arguments?.getString("phoneNumber")

        phoneNumber.text = Html.fromHtml("<u>$mPhoneNumber</u>")
        val timer = object : CountDownTimer(1000 * 60 * 2, 1000) {
            override fun onTick(millisUntilFinished: Long) {

                val minutes = millisUntilFinished / 1000 / 60
                val seconds = millisUntilFinished / 1000 % 60
                resendCode.text = requireContext().resources.getString(
                    R.string.resend_code_in,
                    "$minutes:$seconds"
                )
            }

            override fun onFinish() {
                resendCode.text =
                    Html.fromHtml("<u>${requireContext().resources.getString(R.string.resend_code)}</u>")
                resendCode.setTextColor(requireContext().resources.getColor(R.color.green))
            }
        }
        timer.start()

        requireView().isFocusableInTouchMode = true
        requireView().requestFocus()
        requireView().setOnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                Navigation.findNavController(requireView()).popBackStack()
                timer.cancel()
                true
            } else false
        }

        back.setOnClickListener {
            Navigation.findNavController(requireView()).popBackStack()
            timer.cancel()
        }

        change.setOnClickListener {
            Navigation.findNavController(requireView()).popBackStack()
            timer.cancel()
        }

        resendCode.setOnClickListener {
            timer.cancel()
            val action =
                OTPFragmentDirections
                    .otpToProfile()
            Navigation.findNavController(resendCode).navigate(action)
        }

        /*   wrongOtp.isVisible=true
           tv1.setBackgroundResource(R.drawable.ic_red_square)
           tv2.setBackgroundResource(R.drawable.ic_red_square)
           tv3.setBackgroundResource(R.drawable.ic_red_square)
           tv4.setBackgroundResource(R.drawable.ic_red_square)
           tv5.setBackgroundResource(R.drawable.ic_red_square)
           tv6.setBackgroundResource(R.drawable.ic_red_square)


           tv1.setBackgroundResource(R.drawable.ic_green_square)
           tv2.setBackgroundResource(R.drawable.ic_green_square)
           tv3.setBackgroundResource(R.drawable.ic_green_square)
           tv4.setBackgroundResource(R.drawable.ic_green_square)
           tv5.setBackgroundResource(R.drawable.ic_green_square)
           tv6.setBackgroundResource(R.drawable.ic_green_square)*/

    }

    override fun setObservers() {
    }
}