package com.civil.easyday.screens.activities.main.home


import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.civil.easyday.R
import com.civil.easyday.app.sources.local.interfaces.ProjectInterface
import com.civil.easyday.app.sources.local.prefrences.AppPreferencesDelegates
import com.civil.easyday.screens.activities.main.dashboard.DashboardFragmentDirections
import com.civil.easyday.screens.base.BaseFragment
import com.civil.easyday.screens.dialogs.ProjectListDialog
import com.civil.easyday.utils.DeviceUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*
import me.toptas.fancyshowcase.FancyShowCaseQueue
import me.toptas.fancyshowcase.FancyShowCaseView
import me.toptas.fancyshowcase.listener.OnViewInflateListener


@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeViewModel>(), ProjectInterface {

    companion object {
        const val TAG = "HomeFragment"
    }

    private lateinit var queue: FancyShowCaseQueue
    private lateinit var fancyView2: FancyShowCaseView
    private lateinit var fancyView1: FancyShowCaseView


    override fun getContentView() = R.layout.fragment_home


    override fun initUi() {

        AppPreferencesDelegates.get().token =
            "40CbCrpB0LWI4euNF3C3u2XZETOqQvOB2dbR8gRYGV3AeVtHpSqzeV8dQPCi5Guxvxb9E0APt1HiHM8Z1tlgWtZvRLbtRw3DWWNQaP9Nc14H"

        if (!AppPreferencesDelegates.get().showcaseSeen) {

            fancyView2 = FancyShowCaseView.Builder(requireActivity())
                .customView(R.layout.showcaseview2, object : OnViewInflateListener {
                    override fun onViewInflated(view: View) {
                        setAnimatedContent(view, fancyView2)
                    }
                })
                .closeOnTouch(false)
                .build()

            fancyView1 = FancyShowCaseView.Builder(requireActivity())
                .customView(R.layout.showcaseview1, object : OnViewInflateListener {
                    override fun onViewInflated(view: View) {
                        setAnimatedContent(view, fancyView1)
                    }
                })
                .closeOnTouch(false)
                .build()

            queue = FancyShowCaseQueue().apply {
                add(fancyView2)
                add(fancyView1)
                show()
            }
        }

    }

    override fun setObservers() {
        viewModel.projectList.observe(viewLifecycleOwner) { projectList ->
            activeProject.setOnClickListener {
                DeviceUtils.showProgress()
                if (projectList != null) {
                    val fragment = ProjectListDialog(this, projectList)
                    childFragmentManager.let {
                        fragment.show(it, "projects")
                        DeviceUtils.dismissProgress()
                    }
                }

            }
        }
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
                    AppPreferencesDelegates.get().showcaseSeen = true
                    queue.cancel(true)
                }
                text2.startAnimation(mainAnimation)
            } else {
                val done = view.findViewById<View>(R.id.done) as TextView
                val text1 = view.findViewById<View>(R.id.text1) as TextView
                done.setOnClickListener {
                    AppPreferencesDelegates.get().showcaseSeen = true
                    queue.cancel(true)
                }
                Handler().postDelayed({ text1.startAnimation(subAnimation) }, 80)
            }
        }, 200)
    }

    override fun onClickProject(id: Int) {
        if (id == -1) {
//            Create New Project
            val action = DashboardFragmentDirections.dashboardToAddProject()
            val nav: NavController = Navigation.findNavController(requireView())
            if (nav.currentDestination != null && nav.currentDestination?.id == R.id.dashboardFragment) {
                nav.navigate(action)
            }
        } else {
//            Switch with ProjectID
        }
    }

}
