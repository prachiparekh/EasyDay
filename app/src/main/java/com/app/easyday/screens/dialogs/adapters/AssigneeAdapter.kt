package com.app.easyday.screens.dialogs.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.easyday.R
import com.app.easyday.app.sources.local.interfaces.AttributeSelectionInterface
import com.app.easyday.app.sources.remote.model.UserModel
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.request.RequestOptions

class AssigneeAdapter (
    private val context: Context,
    private var assigneeList: java.util.ArrayList<UserModel>,
    private var selectedAssigneeList: java.util.ArrayList<UserModel>,
    val attrInterface: AttributeSelectionInterface,
    val attributeType:Int
) : RecyclerView.Adapter<AssigneeAdapter.ViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun getItemCount(): Int = assigneeList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.item_participant, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(position)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var participantName = itemView.findViewById<TextView>(R.id.participantName)
        var avatar = itemView.findViewById<ImageView>(R.id.avatar)
        var selection = itemView.findViewById<ImageView>(R.id.selection)

        @SuppressLint("NewApi")
        fun bind(position: Int) {

            participantName.text = assigneeList[position].fullname

            val options = RequestOptions()
            avatar.clipToOutline = true
            if (assigneeList[position].profileImage != "null") {
                Glide.with(context)
                    .load(assigneeList[position].profileImage)
                    .error(context.resources.getDrawable(R.drawable.ic_profile_circle))
                    .apply(
                        options.centerCrop()
                            .skipMemoryCache(true)
                            .priority(Priority.HIGH)
                            .format(DecodeFormat.PREFER_ARGB_8888)
                    )
                    .into(avatar)
            }else{
                Glide.with(context)
                    .load(context.resources.getDrawable(R.drawable.ic_profile_circle))
                    .apply(
                        options.centerCrop()
                            .skipMemoryCache(true)
                            .priority(Priority.HIGH)
                            .format(DecodeFormat.PREFER_ARGB_8888)
                    )
                    .into(avatar)
            }

            if (selectedAssigneeList.contains(assigneeList[position])) {
                selection.setImageDrawable(context.resources.getDrawable(R.drawable.ic_check_radio))
            } else {
                selection.setImageDrawable(context.resources.getDrawable(R.drawable.ic_uncheck_radio))
            }

            itemView.setOnClickListener {
                if (selectedAssigneeList.contains(assigneeList[position])) {
                    selectedAssigneeList.remove(assigneeList[position])
                } else {
                    selectedAssigneeList.add(assigneeList[position])
                }

                notifyDataSetChanged()
            }
        }
    }
}