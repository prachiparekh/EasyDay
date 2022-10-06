package com.app.easyday.screens.dialogs.adapters

import android.R.color
import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.widget.TextViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.app.easyday.R
import com.app.easyday.app.sources.local.interfaces.ProjectInterface
import com.app.easyday.app.sources.remote.model.ProjectRespModel


class ProjectAdapter (
    private val context: Context,
    private var projectList: java.util.ArrayList<ProjectRespModel>,
    val projectInterface: ProjectInterface
) : RecyclerView.Adapter<ProjectAdapter.ViewHolder>() {


    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun getItemCount(): Int = projectList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.item_project, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(position)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val projectName=itemView.findViewById<TextView>(R.id.projectName)
        val description=itemView.findViewById<TextView>(R.id.description)
        val radio=itemView.findViewById<ImageView>(R.id.radio)

        @SuppressLint("NewApi")
        fun bind(position: Int) {

            projectName.text=projectList[position].projectName
            description.text=projectList[position].description

            Log.e("assignColor", projectList[position].assignColor.toString())
            TextViewCompat.setCompoundDrawableTintList(
                projectName,
                ColorStateList.valueOf(
                    Color.parseColor(projectList[position].assignColor)
                )
            )

            itemView.setOnClickListener {
                projectList[position].id?.let { it1 -> projectInterface.onClickProject(it1) }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(list: List<ProjectRespModel>?) {

        this.projectList = ArrayList(list ?: listOf())
        notifyDataSetChanged()
    }

}