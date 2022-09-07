package com.civil.easyday.screens.activities.main.home


import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.TextView
import com.civil.easyday.R
import com.civil.easyday.screens.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import me.toptas.fancyshowcase.FancyShowCaseQueue
import me.toptas.fancyshowcase.FancyShowCaseView
import me.toptas.fancyshowcase.listener.OnViewInflateListener


@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeViewModel>() {

    companion object {
        const val TAG = "HomeFragment"
    }

    var showCaseDone = false
    private lateinit var queue: FancyShowCaseQueue
    private lateinit var fancyView2: FancyShowCaseView
    private lateinit var fancyView1: FancyShowCaseView

    override fun getContentView() = R.layout.fragment_home

    override fun initUi() {


        if (!showCaseDone) {

            fancyView2 = FancyShowCaseView.Builder(requireActivity())
                .customView(R.layout.showcaseview2, object : OnViewInflateListener {
                    override fun onViewInflated(view: View) {
                        setAnimatedContent(view, fancyView2)
                    }
                })
                .build()

            fancyView1 = FancyShowCaseView.Builder(requireActivity())
                .customView(R.layout.showcaseview1, object : OnViewInflateListener {
                    override fun onViewInflated(view: View) {
                        setAnimatedContent(view, fancyView1)
                    }
                })
                .build()

            queue = FancyShowCaseQueue().apply {
                add(fancyView2)
                add(fancyView1)
                show()
            }
        }

    }

    override fun setObservers() {
    }

    private fun setAnimatedContent(view: View, fancyShowCaseView: FancyShowCaseView) {
        Handler().postDelayed({

            val mainAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_in_left)
            mainAnimation.fillAfter = true

            val subAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_in_left)
            subAnimation.fillAfter = true

            if (fancyShowCaseView == fancyView2) {
                val ctaNext = view.findViewById<View>(R.id.ctaNext) as TextView
                val text2 = view.findViewById<View>(R.id.text2) as TextView
                val skip = view.findViewById<View>(R.id.skip) as TextView
                ctaNext.setOnClickListener { fancyShowCaseView.hide() }
                skip.setOnClickListener {
                    showCaseDone = true
                    queue.cancel(true)
                }
                text2.startAnimation(mainAnimation)
            } else {
                val done = view.findViewById<View>(R.id.done) as TextView
                val text1 = view.findViewById<View>(R.id.text1) as TextView
                done.setOnClickListener {
                    showCaseDone = true
                    queue.cancel(true)
                }
                Handler().postDelayed({ text1.startAnimation(subAnimation) }, 80)
            }
        }, 200)
    }

}
