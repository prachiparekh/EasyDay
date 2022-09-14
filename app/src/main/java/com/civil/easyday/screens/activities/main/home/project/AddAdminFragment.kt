package com.civil.easyday.screens.activities.main.home.project

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.civil.easyday.R
import com.civil.easyday.app.sources.local.model.ContactModel
import com.civil.easyday.databinding.FragmentAddAdminBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AddAdminFragment : Fragment() {

    var binding: FragmentAddAdminBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_admin, container, false)
        var selectedList =
            arguments?.getParcelableArrayList<ContactModel>("selectedParticipantList")
        Log.e("selectedList", selectedList.toString())
        return binding?.root
    }

}