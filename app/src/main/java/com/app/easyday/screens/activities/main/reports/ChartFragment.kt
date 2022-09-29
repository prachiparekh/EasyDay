package com.app.easyday.screens.activities.main.reports

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.easyday.R
import com.app.easyday.screens.base.BaseFragment
import com.faskn.lib.PieChart
import com.faskn.lib.Slice
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_chart.*

@AndroidEntryPoint
class ChartFragment : BaseFragment<ChartViewModel>() {

    companion object
    {
        val tag="Chart"
    }

    override fun getContentView()=R.layout.fragment_chart

    override fun initUi() {
        val pieChart = PieChart(
            slices = provideSlices(), clickListener = null, sliceStartPoint = 0f, sliceWidth = 50f
        ).build()

        taskChart.setPieChart(pieChart)

    }

    override fun setObservers() {

    }

    private fun provideSlices(): ArrayList<Slice> {
        return arrayListOf(
            Slice(
                20F,
                R.color.sky_blue,
                requireContext().resources.getString(R.string.inprogress)
            ),
            Slice(
                12F,
                R.color.yellow,
                requireContext().resources.getString(R.string.review)
            ),
            Slice(
                44F,
                R.color.green,
                requireContext().resources.getString(R.string.completed)
            ),
            Slice(
                86F,
                R.color.red,
                requireContext().resources.getString(R.string.reopened)
            )
        )
    }


}