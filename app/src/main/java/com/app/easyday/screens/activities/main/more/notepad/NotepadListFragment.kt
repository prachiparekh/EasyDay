package com.app.easyday.screens.activities.main.more.notepad

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.app.easyday.R
import com.app.easyday.databinding.FragmentNotepadListBinding

class NotepadListFragment : Fragment() {

var binding: FragmentNotepadListBinding?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_notepad_list, container, false)

        binding?.createNote?.setOnClickListener {
            val direction = NotepadListFragmentDirections.notepadListToCreateNote()
            Navigation.findNavController(requireView()).navigate(direction)
        }

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().window?.statusBarColor = resources.getColor(R.color.navy_blue)
    }


}