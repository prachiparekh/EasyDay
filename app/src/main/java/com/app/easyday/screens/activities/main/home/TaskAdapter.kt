package com.app.easyday.screens.activities.main.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.app.easyday.R
import com.app.easyday.app.sources.remote.model.TaskAttributeResponse
import com.app.easyday.app.sources.remote.model.TaskResponse
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class TaskAdapter(
    private val context: Context,
    private var taskList: ArrayList<TaskResponse>
) : RecyclerView.Adapter<TaskAdapter.ViewHolder>() {


    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun getItemCount(): Int = taskList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.item_task, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val taskTitle = itemView.findViewById<TextView>(R.id.taskTitle)
        val mediaPager = itemView.findViewById<ViewPager2>(R.id.mediaPager)
        val mDate = itemView.findViewById<TextView>(R.id.mDate)
        val mDescription = itemView.findViewById<TextView>(R.id.mDescription)
        val tagRV = itemView.findViewById<RecyclerView>(R.id.tagRV)
        val dots_indicator = itemView.findViewById<DotsIndicator>(R.id.dots_indicator)
        val flag = itemView.findViewById<ImageView>(R.id.flag)

        @SuppressLint("NewApi")
        fun bind(position: Int) {

            val item = taskList[position]
            taskTitle.text = item.title

            val odt = OffsetDateTime.parse(item.createdAt);
            val dtf = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH)

            mDate.text = dtf.format(odt)
            mDescription.text = item.description

           if(item.redFlag == true){
               flag.setImageDrawable(context.resources.getDrawable(R.drawable.ic_flaged))
           }else{
               flag.setImageDrawable(context.resources.getDrawable(R.drawable.ic_flg))
           }

            var mediaAdapter: TaskMediaAdapter? = null
            mediaAdapter = TaskMediaAdapter(
                context,
                onItemClick = { isVideo, uri ->
                    if (isVideo) {
                        val play = Intent(Intent.ACTION_VIEW, uri.toUri())
                        play.setDataAndType(
                            uri.toUri(),
                            "video/mp4"
                        )
                        context.startActivity(play)
                    }
                },

                )

            mediaPager.apply {
                adapter = mediaAdapter.apply { submitList(item.taskMedia) }
            }

            dots_indicator.attachTo(mediaPager)

            tagRV.adapter = TaskTagAdapter(
                context,
                item.taskTags as java.util.ArrayList<TaskAttributeResponse>
            )
        }
    }


}