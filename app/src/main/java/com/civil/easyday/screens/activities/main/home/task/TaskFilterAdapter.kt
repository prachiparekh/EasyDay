package com.civil.easyday.screens.activities.main.home.task

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.civil.easyday.R
import com.civil.easyday.app.sources.local.interfaces.FilterTypeInterface

class TaskFilterAdapter(
    private val context: Context,
    private var filterList: java.util.ArrayList<String>,
    private var priorityList: java.util.ArrayList<String>,
    private var drawableList: java.util.ArrayList<Drawable>,
    val filterTypeInterface: FilterTypeInterface
) : RecyclerView.Adapter<TaskFilterAdapter.ViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun getItemCount(): Int = filterList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.item_task_filter, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(position)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val filterName = itemView.findViewById<TextView>(R.id.label)

        @SuppressLint("NewApi")
        fun bind(position: Int) {

            filterName.text = filterList[position]
            filterName.setCompoundDrawablesWithIntrinsicBounds(drawableList[position], null, null, null)

            itemView.setOnClickListener {
                if(position==0){

                }else {
                    position.let { it1 -> filterTypeInterface.onFilterTypeClick(it1) }
                }
            }
        }
    }
}