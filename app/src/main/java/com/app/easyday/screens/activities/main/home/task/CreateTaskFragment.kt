package com.app.easyday.screens.activities.main.home.task

import android.graphics.drawable.Drawable
import android.util.Log
import androidx.navigation.Navigation
import com.app.easyday.R
import com.app.easyday.app.sources.local.interfaces.FilterCloseInterface
import com.app.easyday.app.sources.local.interfaces.FilterTypeInterface
import com.app.easyday.app.sources.local.interfaces.TagInterface
import com.app.easyday.screens.base.BaseFragment
import com.app.easyday.screens.dialogs.AddTagBottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_create_task.*

@AndroidEntryPoint
class CreateTaskFragment : BaseFragment<CreateTaskViewModel>(), FilterTypeInterface, TagInterface,FilterCloseInterface {

    override fun getContentView() = R.layout.fragment_create_task
    private var filterTypeList = arrayListOf<String>()
    private var drawableList = arrayListOf<Drawable>()
    private var priorityList = arrayListOf<String>()
    var adapter:TaskFilterAdapter?=null

//    *****************

    var selectedTagList = arrayListOf<String>()
    private var selectedPriority: String? = null
    var redFlag=false

//    *****************

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

        priorityList.add(requireContext().resources.getString(R.string.none))
        priorityList.add(requireContext().resources.getString(R.string.low))
        priorityList.add(requireContext().resources.getString(R.string.normal))
        priorityList.add(requireContext().resources.getString(R.string.high))
        selectedPriority = priorityList[0]

        close.setOnClickListener {
            Navigation.findNavController(requireView()).popBackStack()
        }

        adapter=TaskFilterAdapter(requireContext(), filterTypeList, priorityList, drawableList, this)
        filterRV.adapter =adapter

    }

    override fun setObservers() {

    }

    override fun onFilterTypeClick(position: Int) {
        Log.e("filter", filterTypeList[position])
        when (filterTypeList[position]) {
            requireContext().resources.getString(R.string.f_tag) -> {
                val tagList= arrayListOf<String>()
                tagList.add("Tag1")
                tagList.add("Tag2")
                tagList.add("Tag3")
                tagList.add("Tag4")
                tagList.add("Tag5")

                val selectedTagList = arrayListOf<String>()
                selectedTagList.add(tagList[0])
                selectedTagList.add(tagList[1])

                val fragment =
                    AddTagBottomSheetDialog(requireContext(), tagList, selectedTagList, this,this)
                childFragmentManager.let {
                    fragment.show(it, "tags")
                }
            }
            requireContext().resources.getString(R.string.f_red_flag)->{

            }
        }
    }

    override fun onFilterSingleChildClick(childList: ArrayList<String>, childPosition: Int) {
        selectedPriority = childList[childPosition]
    }

    override fun onFilterMultipleChildClick() {
    }

    override fun onFilterFlagClick(redFlag: Boolean) {
       this.redFlag=redFlag
    }

    override fun onClickTag(selectedTagList: ArrayList<String>) {
        this.selectedTagList=selectedTagList
    }

    override fun onCloseClick() {
        adapter?.closeFilter()
    }


}