package com.app.easyday.screens.activities.main.home.filter

import android.app.DatePickerDialog
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import com.app.easyday.R
import com.app.easyday.app.sources.local.interfaces.FilterTypeInterface
import com.app.easyday.screens.base.BaseFragment
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.date_range_filter_layout.view.*
import kotlinx.android.synthetic.main.fragment_filter.*
import kotlinx.android.synthetic.main.other_filter_layout.view.*
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class FilterFragment : BaseFragment<FilterViewModel>(), FilterTypeInterface {

    companion object {
        var selectedFilterPosition = 0
    }

    private var filterTypeList = arrayListOf<String>()
    private var taskStatusList = arrayListOf<String>()
    var priorityList = arrayListOf<String>()
    var redFlagList = arrayListOf<String>()
    var dueDateList = arrayListOf<String>()

    override fun getContentView() = R.layout.fragment_filter

    override fun initUi() {
        filterTypeList.add(requireContext().resources.getString(R.string.f_date_range))
        filterTypeList.add(requireContext().resources.getString(R.string.f_task_status))
        filterTypeList.add(requireContext().resources.getString(R.string.f_tag))
        filterTypeList.add(requireContext().resources.getString(R.string.f_priority))
        filterTypeList.add(requireContext().resources.getString(R.string.f_assigned_To))
        filterTypeList.add(requireContext().resources.getString(R.string.f_zone))
        filterTypeList.add(requireContext().resources.getString(R.string.f_space))
        filterTypeList.add(requireContext().resources.getString(R.string.f_red_flag))
        filterTypeList.add(requireContext().resources.getString(R.string.f_due_date))

        filterRV.adapter = FilterTypeAdapter(requireContext(), filterTypeList, this)

        taskStatusList.add(requireContext().resources.getString(R.string.progress))
        taskStatusList.add(requireContext().resources.getString(R.string.review))
        taskStatusList.add(requireContext().resources.getString(R.string.completed))
        taskStatusList.add(requireContext().resources.getString(R.string.reopened))

        priorityList.add(requireContext().resources.getString(R.string.low))
        priorityList.add(requireContext().resources.getString(R.string.normal))
        priorityList.add(requireContext().resources.getString(R.string.high))

        redFlagList.add(requireContext().resources.getString(R.string.yes))
        redFlagList.add(requireContext().resources.getString(R.string.no))

        dueDateList.add(requireContext().resources.getString(R.string.passed))
        dueDateList.add(requireContext().resources.getString(R.string.not_passed))

        changeFilterUI(selectedFilterPosition)
    }

    override fun setObservers() {
    }

    override fun onFilterTypeClick(position: Int) {
        selectedFilterPosition = position
        changeFilterUI(selectedFilterPosition)
    }

    override fun onFilterSingleChildClick(childList: ArrayList<String>, childPosition: Int) {
        Log.e("selected:", childList[childPosition])
    }

    override fun onFilterMultipleChildClick() {

    }

    private fun changeFilterUI(position: Int) {
        if (position == 0) {
//            DueDate Filter
            dateRangeLayout.isVisible = true
            otherLayout.isVisible = false
            dueDateFilterHandler()
        } else {
//            Other Filter
            dateRangeLayout.isVisible = false
            otherLayout.isVisible = true
            otherFilterHandler(position)
        }
    }

    private fun dueDateFilterHandler() {
        dateRangeLayout.fromET?.setOnClickListener {
            showDatePicker(it, Date().time)
        }

        dateRangeLayout.toET?.setOnClickListener {
            showDatePicker(it, Date().time)
        }
    }

    private fun otherFilterHandler(parentPosition: Int) {
        when (parentPosition) {
            1 -> {
//                Single : Task Status
                otherLayout.childFilterRV.adapter =
                    FilterSingleChildAdapter(requireContext(), taskStatusList, this)
            }
            2 -> {
//                 Multiple :Tag (API)
                otherLayout.childFilterRV.adapter =
                    FilterMultipleChildAdapter(requireContext(), arrayListOf(), arrayListOf())
            }
            3 -> {
//                Single : Priority
                otherLayout.childFilterRV.adapter =
                    FilterSingleChildAdapter(requireContext(), priorityList, this)
            }
            4 -> {
//                 Multiple :Assigned To (API)
                otherLayout.childFilterRV.adapter =
                    FilterMultipleChildAdapter(requireContext(), arrayListOf(), arrayListOf())
            }
            5 -> {
//                 Multiple :Zone (API)
                otherLayout.childFilterRV.adapter =
                    FilterMultipleChildAdapter(requireContext(), arrayListOf(), arrayListOf())
            }
            6 -> {
//                 Multiple :Space (API)
                otherLayout.childFilterRV.adapter =
                    FilterMultipleChildAdapter(requireContext(), arrayListOf(), arrayListOf())
            }
            7 -> {
//                 Single :Red Flag
                otherLayout.childFilterRV.adapter =
                    FilterSingleChildAdapter(requireContext(), redFlagList, this)
            }
            8  -> {
//                 Single :Due Date
                otherLayout.childFilterRV.adapter =
                    FilterSingleChildAdapter(requireContext(), dueDateList, this)
            }

        }

    }

    private var dpd: DatePickerDialog? = null
    private fun showDatePicker(view: View, dateInMillis: Long) {
        val c = Calendar.getInstance().apply {
            timeInMillis = dateInMillis
        }

        dpd?.cancel()
        dpd = DatePickerDialog(
            requireContext(), R.style.DialogTheme, { _, year, monthOfYear, dayOfMonth ->

                if (view.id == R.id.fromET) {
                    dateRangeLayout.fromLabel?.setEndIconDrawable(R.drawable.ic_calendar_visible)
                    dateRangeLayout.fromLabel?.endIconMode = TextInputLayout.END_ICON_CUSTOM
                } else {
                    dateRangeLayout.toLabel?.setEndIconDrawable(R.drawable.ic_calendar_visible)
                    dateRangeLayout.toLabel?.endIconMode = TextInputLayout.END_ICON_CUSTOM
                }

                setDueDate(
                    view, Calendar.getInstance().apply {
                        set(Calendar.YEAR, year)
                        set(Calendar.MONTH, monthOfYear)
                        set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    }.timeInMillis
                )
            }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)
        )

        dpd?.show()
    }

    private var fromDateMillis: Long = 0
    private fun setDueDate(view: View, timeInMillies: Long) {

        if (view.id == R.id.fromET) {
            fromDateMillis = timeInMillies
        }
        if (!fromDateMillis.equals(0)) {
            if (view.id == R.id.toET) {
                if (fromDateMillis > timeInMillies) {
                    Toast.makeText(
                        requireContext(),
                        requireContext().resources.getString(R.string.to_date_error),
                        Toast.LENGTH_SHORT
                    ).show()
                    return
                }
            }
        }
        (view as? TextView)?.text = SimpleDateFormat(
            getString(R.string.date_format_template), Locale.getDefault()
        ).format(
            Date(timeInMillies)
        )

    }


}