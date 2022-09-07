package com.civil.easyday.screens.activities.main.home


import android.view.View
import android.widget.TextView
import com.civil.easyday.R
import com.civil.easyday.screens.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import me.toptas.fancyshowcase.FancyShowCaseView
import me.toptas.fancyshowcase.listener.OnViewInflateListener


@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeViewModel>() {

    companion object {
        const val TAG = "HomeFragment"
    }

    var showCaseDone = false

    override fun getContentView() = R.layout.fragment_home

    override fun initUi() {

        var showcaseView2: FancyShowCaseView? = null
        var showcaseView1: FancyShowCaseView? = null
        if (!showCaseDone) {
            showcaseView2 = FancyShowCaseView.Builder(requireActivity())
                .customView(R.layout.showcaseview2, object : OnViewInflateListener {
                    override fun onViewInflated(view: View) {
                        view.findViewById<View>(R.id.ctaNext).setOnClickListener {
                            showcaseView2?.hide()

                            showcaseView1 = FancyShowCaseView.Builder(requireActivity())
                                .customView(R.layout.showcaseview1, object : OnViewInflateListener {
                                    override fun onViewInflated(view: View) {
                                        view.findViewById<View>(R.id.done).setOnClickListener {
                                            showCaseDone = true
                                            showcaseView1?.hide()
                                        }
                                    }
                                })
                                .fitSystemWindows(true)
                                .build()

                            showcaseView1?.show()
                        }
                        view.findViewById<View>(R.id.skip).setOnClickListener {
                            showCaseDone = true
                            showcaseView2?.hide()
                        }
                    }

                })
                .fitSystemWindows(true)
                .build()
            showcaseView2.show()
        }

    }

    override fun setObservers() {
    }

}
