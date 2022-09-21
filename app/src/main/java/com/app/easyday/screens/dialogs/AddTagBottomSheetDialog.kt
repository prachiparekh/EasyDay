package com.app.easyday.screens.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.app.easyday.R
import com.app.easyday.app.sources.local.interfaces.FilterCloseInterface
import com.app.easyday.app.sources.local.interfaces.TagInterface
import com.app.easyday.databinding.AddTagLayoutBinding
import com.app.easyday.screens.dialogs.adapters.ProjectAdapter
import com.app.easyday.screens.dialogs.adapters.TagsAdapter
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class AddTagBottomSheetDialog (
    var mContext: Context,
    var tagList: ArrayList<String>,
    private var selectedTagList: java.util.ArrayList<String>,
    var tagInterface: TagInterface,
    var closeInterface: FilterCloseInterface
) :
    BottomSheetDialogFragment() {
    var binding: AddTagLayoutBinding? = null
    var adapter: ProjectAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.add_tag_layout, container, false)
        isCancelable = false

        binding?.tagRV?.layoutManager= FlexboxLayoutManager(requireContext())
        binding?.tagRV?.adapter=TagsAdapter(mContext,tagList,selectedTagList,tagInterface)

        binding?.close?.setOnClickListener {
            closeInterface.onCloseClick()
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
            }
        }

        return dialog
    }

}
