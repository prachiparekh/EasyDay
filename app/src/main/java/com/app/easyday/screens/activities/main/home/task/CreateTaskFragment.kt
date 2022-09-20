package com.app.easyday.screens.activities.main.home.task

import android.graphics.drawable.Drawable
import android.util.Log
import androidx.navigation.Navigation
import com.app.easyday.R
import com.app.easyday.app.sources.local.interfaces.FilterTypeInterface
import com.app.easyday.screens.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_create_task.*

@AndroidEntryPoint
class CreateTaskFragment : BaseFragment<CreateTaskViewModel>(), FilterTypeInterface {


    override fun getContentView() = R.layout.fragment_create_task

    private var filterTypeList = arrayListOf<String>()
    private var drawableList = arrayListOf<Drawable>()
    private var priorityList = arrayListOf<String>()

    override fun initUi() {

        filterTypeList.add(requireContext().resources.getString(R.string.f_priority))
        filterTypeList.add(requireContext().resources.getString(R.string.f_tag))
        filterTypeList.add(requireContext().resources.getString(R.string.f_red_flag))
        filterTypeList.add(requireContext().resources.getString(R.string.f_space))
        filterTypeList.add(requireContext().resources.getString(R.string.f_zone))
        filterTypeList.add(requireContext().resources.getString(R.string.f_due_date))

        drawableList.add(requireContext().resources.getDrawable(R.drawable.ic_priority))
        drawableList.add(requireContext().resources.getDrawable(R.drawable.ic_tag))
        drawableList.add(requireContext().resources.getDrawable(R.drawable.ic_flag))
        drawableList.add(requireContext().resources.getDrawable(R.drawable.ic_buliding))
        drawableList.add(requireContext().resources.getDrawable(R.drawable.ic_zone))
        drawableList.add(requireContext().resources.getDrawable(R.drawable.ic_calender))

        priorityList.add(requireContext().resources.getString(R.string.low))
        priorityList.add(requireContext().resources.getString(R.string.normal))
        priorityList.add(requireContext().resources.getString(R.string.high))

        close.setOnClickListener {
            Navigation.findNavController(requireView()).popBackStack()
        }

        filterRV.adapter =
            TaskFilterAdapter(requireContext(), filterTypeList, priorityList, drawableList, this)
    }

    override fun setObservers() {

    }

    override fun onFilterTypeClick(position: Int) {
        Log.e("filter", filterTypeList[position])
    }

    override fun onFilterSingleChildClick(childList: ArrayList<String>, childPosition: Int) {
        Log.e("childfilter", childList[childPosition])
    }

    override fun onFilterMultipleChildClick() {
    }


}