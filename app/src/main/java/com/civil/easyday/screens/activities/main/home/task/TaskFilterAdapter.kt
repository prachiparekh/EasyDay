package com.civil.easyday.screens.activities.main.home.task

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
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
    var selectedPosition = -1


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
        val close = itemView.findViewById<ImageView>(R.id.close)
        val childRV = itemView.findViewById<RecyclerView>(R.id.childRV)

        @SuppressLint("NewApi")
        fun bind(position: Int) {

            filterName.text = filterList[position]
            filterName.setCompoundDrawablesWithIntrinsicBounds(
                drawableList[position],
                null,
                null,
                null
            )


            itemView.setOnClickListener {
//                Log.e("clickPosition $position", filterList[position])

                when (position) {
                    0 -> {
//                        Log.e("adapterPosition $position", filterList[position])
                        childRV.adapter =
                            FilterChildAdapter(context, priorityList, filterTypeInterface)
                        close.isVisible = true
                    }
                    1, 3, 4, 5 -> {
//                        Log.e("elsePosition $position", filterList[position])
                        childRV.adapter = null
                        close.isVisible = true
                    }
                    2 -> {
//                        Log.e("flagPosition $position", filterList[position])
                        setTextViewDrawableColor(filterName, R.color.red)
                        childRV.adapter = null
                        close.isVisible = false
                    }
                }

                if (selectedPosition != -1) {
                    selectedPosition = position
                    notifyDataSetChanged()
                }
            }

            if (selectedPosition != -1) {
//                Log.e("Position $position", filterList[position])
                if (selectedPosition != position) {
//                    Log.e("closePosition $position", filterList[position])
                    close.isVisible = false
                }
            }

            close.setOnClickListener {
                close.isVisible = false
                if (position == 0)
                    childRV.adapter = null
            }
        }
    }

    private fun setTextViewDrawableColor(textView: TextView, color: Int) {
        for (drawable in textView.compoundDrawables) {
            if (drawable != null) {
                drawable.colorFilter =
                    PorterDuffColorFilter(
                        ContextCompat.getColor(textView.context, color),
                        PorterDuff.Mode.SRC_IN
                    )
            }
        }
    }
}