package com.app.easyday.screens.activities.main.home.filter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.easyday.R
import com.app.easyday.app.sources.remote.model.AttributeResponse
import com.app.easyday.screens.activities.main.home.filter.FilterFragment.Companion.filterSpaceList
import com.app.easyday.screens.activities.main.home.filter.FilterFragment.Companion.filterTagList
import com.app.easyday.screens.activities.main.home.filter.FilterFragment.Companion.filterZoneList

class FilterMultipleChildAdapter(
    private val context: Context,
    private var filterChildList: java.util.ArrayList<AttributeResponse>,
    private var selectedChildPositionList: java.util.ArrayList<Int>,
    val type: Int
) : RecyclerView.Adapter<FilterMultipleChildAdapter.ViewHolder>() {

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

            childName.text = filterChildList[position].attributeName

            if (selectedChildPositionList.contains(filterChildList[position].id)) {
                selection.setImageDrawable(context.resources.getDrawable(R.drawable.ic_check_radio))
            } else {
                selection.setImageDrawable(context.resources.getDrawable(R.drawable.ic_uncheck_radio))
            }

            itemView.setOnClickListener {
                if (!selectedChildPositionList.contains(filterChildList[position].id)) {
                    filterChildList[position].id?.let { it1 -> selectedChildPositionList.add(it1) }
                    selection.setImageDrawable(context.resources.getDrawable(R.drawable.ic_check_radio))

                    Log.e("filterSpaceList", filterSpaceList.toString())
                    when (type) {
                        0 -> filterChildList[position].id?.let { it1 -> filterTagList.add(it1) }
                        1 -> filterChildList[position].id?.let { it1 -> filterZoneList.add(it1) }
                        2 -> filterChildList[position].id?.let { it1 -> filterSpaceList.add(it1) }
                    }
                    Log.e("filterSpaceList", filterSpaceList.toString())
                } else {
                    selectedChildPositionList.remove(filterChildList[position].id)
                    selection.setImageDrawable(context.resources.getDrawable(R.drawable.ic_uncheck_radio))

                    when (type) {
                        0 -> filterTagList.remove(filterChildList[position].id)
                        1 -> filterZoneList.remove(filterChildList[position].id)
                        2 -> filterSpaceList.remove(filterChildList[position].id)
                    }
                }
            }
        }
    }

    fun getMultiplePositionList(): ArrayList<Int> {
        return selectedChildPositionList
    }
}