package com.civil.easyday.screens.activities.main.home.project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.civil.easyday.R
import com.civil.easyday.app.sources.local.interfaces.ColorInterface
import com.civil.easyday.app.sources.remote.model.AddProjectRequestModel
import com.civil.easyday.databinding.FragmentAddProjectBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddProjectFragment : Fragment(), ColorInterface {

    var selectedColor = R.color.color1

    val colorList = intArrayOf(
        R.color.color1,
        R.color.color2,
        R.color.color3,
        R.color.color4,
        R.color.color5,
        R.color.color6,
        R.color.color7,
        R.color.color8
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        val binding: FragmentAddProjectBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_add_project, container, false)
        binding.toolBar.setNavigationOnClickListener {
            Navigation.findNavController(requireView()).popBackStack()
        }

        binding.colorRV.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        binding.colorRV.adapter = ColorAdapter(requireContext(), colorList, this)

        binding.cta.setOnClickListener {
            if (binding.projectName.text.isNullOrEmpty()) {
                Toast.makeText(requireContext(), requireContext().resources.getString(R.string.name_required), Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            if (binding.description.text.isNullOrEmpty()) {
                Toast.makeText(
                    requireContext(),
                    requireContext().resources.getString(R.string.desc_required),
                    Toast.LENGTH_SHORT
                )
                    .show()
                return@setOnClickListener
            }

            var createProjectModel = AddProjectRequestModel(
                selectedColor.toString(),
                binding.description.toString(),
                binding.projectName.toString()
            )
        }

        return binding.root
    }

    override fun onColorClick(color: Int) {
        selectedColor = color
    }


}