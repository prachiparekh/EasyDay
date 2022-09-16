package com.civil.easyday.screens.activities.main.home.filter

import android.app.DatePickerDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.civil.easyday.R
import com.civil.easyday.app.sources.local.interfaces.FilterTypeInterface
import com.civil.easyday.databinding.DueDateFilterLayoutBinding
import com.civil.easyday.databinding.OtherFilterLayoutBinding
import com.civil.easyday.screens.base.BaseFragment
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_filter.*
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class FilterFragment : BaseFragment<FilterViewModel>(), FilterTypeInterface {

    companion object {
        var selectedFilterPosition = 0
    }

    var filterTypeList = arrayListOf<String>()
    var dueDateLayoutBinding: DueDateFilterLayoutBinding? = null
    var otherFilterBinding: OtherFilterLayoutBinding? = null


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
       /* dueDateLayoutBinding = DataBindingUtil.inflate<DueDateFilterLayoutBinding>(
            layoutInflater, R.layout.due_date_filter_layout, null, true
        )
        otherFilterBinding =
            DataBindingUtil.inflate<OtherFilterLayoutBinding>(
                layoutInflater,
                R.layout.other_filter_layout,
                null,
                true
            )*/

        dueDateLayoutBinding= dueDateLayoutBinding?.root?.let { DueDateFilterLayoutBinding.bind(it) }
        otherFilterBinding= otherFilterBinding?.root?.let { OtherFilterLayoutBinding.bind(it) }

        changeFilterUI(selectedFilterPosition)
    }

    override fun setObservers() {
    }

    override fun onFilterTypeClick(position: Int) {
        selectedFilterPosition = position
        changeFilterUI(selectedFilterPosition)
    }

    private fun changeFilterUI(position: Int) {
        if (position == 0) {
            dueDateLayout.isVisible = true
            otherLayout.isVisible = false
            dueDateFilterHandler()
        } else {
            dueDateLayout.isVisible = false
            otherLayout.isVisible = true
        }
    }

    private fun dueDateFilterHandler() {
        dueDateLayoutBinding?.fromET?.setOnClickListener {
            Log.e("fromET","fromET")
            showDatePicker(it, Date().time)
        }

        dueDateLayoutBinding?.toET?.setOnClickListener {
            Log.e("toET","toET")
            showDatePicker(it, Date().time)
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
                    dueDateLayoutBinding?.fromLabel?.setEndIconDrawable(R.drawable.ic_calendar_visible)
                    dueDateLayoutBinding?.fromLabel?.endIconMode = TextInputLayout.END_ICON_CUSTOM
                } else {
                    dueDateLayoutBinding?.toLabel?.setEndIconDrawable(R.drawable.ic_calendar_visible)
                    dueDateLayoutBinding?.toLabel?.endIconMode = TextInputLayout.END_ICON_CUSTOM
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

    private fun setDueDate(view: View, timeInMillies: Long) {

        (view as? TextView)?.text = SimpleDateFormat(
            getString(R.string.date_format_template), Locale.getDefault()
        ).format(
            Date(timeInMillies)
        )

    }


}