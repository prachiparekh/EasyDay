package com.app.easyday.screens.activities.main.more.notepad

import android.content.Context
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.app.easyday.R
import com.app.easyday.databinding.FragmentCreateNoteBinding
import com.onegravity.rteditor.RTManager
import com.onegravity.rteditor.api.RTApi
import com.onegravity.rteditor.api.RTMediaFactoryImpl
import com.onegravity.rteditor.api.RTProxyImpl


class CreateNoteFragment : Fragment() {

    override fun onGetLayoutInflater(savedInstanceState: Bundle?): LayoutInflater {
        val inflater = super.onGetLayoutInflater(savedInstanceState)
        val contextThemeWrapper: Context =
            ContextThemeWrapper(requireContext(), com.onegravity.rteditor.R.style.RTE_ThemeLight)

        return inflater.cloneInContext(contextThemeWrapper)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().window?.statusBarColor = resources.getColor(R.color.navy_blue)

    }

    private var mRTManager: RTManager? = null
    var binding: FragmentCreateNoteBinding? = null

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mRTManager?.onSaveInstanceState(outState);
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_create_note, container, false)

        val rtApi = RTApi(
            requireContext(),
            RTProxyImpl(requireActivity()),
            RTMediaFactoryImpl(requireContext(), true)
        )
        mRTManager = RTManager(rtApi, savedInstanceState)

        mRTManager?.registerToolbar(
            binding?.rteToolbarContainer as ViewGroup,
            binding?.rteToolbarCharacter
        )

        mRTManager?.registerEditor(binding?.rtEditText, true)
        binding?.rtEditText?.setRichTextEditing(true, "message")

        return binding?.root
    }

    override fun onDestroy() {
        super.onDestroy()
        mRTManager?.onDestroy(true)
    }

}