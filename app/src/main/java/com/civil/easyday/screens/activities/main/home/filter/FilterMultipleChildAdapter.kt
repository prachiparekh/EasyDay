package com.civil.easyday.screens.activities.main.home.filter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.civil.easyday.R

class FilterMultipleChildAdapter (
    private val context: Context,
    private var filterChildList: java.util.ArrayList<String>,
    private var selectedChildPositionList: java.util.ArrayList<Int>
) : RecyclerView.Adapter<FilterMultipleChildAdapter.ViewHolder>(){

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun getItemCount(): Int = filterChildList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.item_other_filter_selection, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val selection = itemView.findViewById<ImageView>(R.id.selection)
        val childName = itemView.findViewById<TextView>(R.id.childName)

        @SuppressLint("NewApi")
        fun bind(position: Int) {

            childName.text = filterChildList[position]

            if (selectedChildPositionList.contains(position)) {
                selection.setImageDrawable(context.resources.getDrawable(R.drawable.ic_check_radio))
            } else {
                selection.setImageDrawable(context.resources.getDrawable(R.drawable.ic_uncheck_radio))
            }

            itemView.setOnClickListener {
                if (!selectedChildPositionList.contains(position)) {
                    selectedChildPositionList.add(position)
                    selection.setImageDrawable(context.resources.getDrawable(R.drawable.ic_check_radio))
                } else {
                    selectedChildPositionList.remove(position)
                    selection.setImageDrawable(context.resources.getDrawable(R.drawable.ic_uncheck_radio))
                }
            }
        }
    }

    fun getMultiplePositionList(): ArrayList<Int> {
        return selectedChildPositionList
    }
}