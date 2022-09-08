package com.civil.easyday.screens.dialogs

import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import com.civil.easyday.R
import com.civil.easyday.databinding.ProjectListBdialogBinding
import com.civil.easyday.views.Progressbar
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ProjectListDialog : BottomSheetDialogFragment() {
    var binding: ProjectListBdialogBinding? = null
    var progressbar: Progressbar? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.project_list_bdialog, container, false)
        isCancelable = false
        progressbar = Progressbar(requireContext())


        binding?.close?.setOnClickListener {
            dismiss()
        }
        return binding?.root
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext(), theme)
        dialog.setOnShowListener {

            val bottomSheetDialog = it as BottomSheetDialog
            val parentLayout =
                bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            parentLayout?.let { it1 ->
                val behaviour = BottomSheetBehavior.from(it1)
                behaviour.state = BottomSheetBehavior.STATE_EXPANDED

                behaviour.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                    override fun onStateChanged(@NonNull bottomSheet: View, newState: Int) {

                    }

                    override fun onSlide(@NonNull bottomSheet: View, slideOffset: Float) {
                        // React to dragging events
                    }
                })
            }
        }

        return dialog
    }

}
