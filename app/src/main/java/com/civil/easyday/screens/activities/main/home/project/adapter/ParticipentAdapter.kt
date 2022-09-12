package com.civil.easyday.screens.activities.main.home.project.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.request.RequestOptions
import com.civil.easyday.R
import com.civil.easyday.app.sources.local.model.ContactModel

class ParticipentAdapter(
    private val context: Context,
    private var contactList: ArrayList<ContactModel>
) : RecyclerView.Adapter<ParticipentAdapter.ViewHolder>() {

    private var selectedContactList = ArrayList<ContactModel>()
    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun getItemCount(): Int = contactList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.item_participant, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val avatar = itemView.findViewById<ImageView>(R.id.avatar)
        val selection = itemView.findViewById<ImageView>(R.id.selection)
        val participantName = itemView.findViewById<TextView>(R.id.participantName)

        @SuppressLint("NewApi")
        fun bind(position: Int) {

            participantName.text = contactList[position].name

            val options = RequestOptions()
            avatar.clipToOutline = true
            Glide.with(context)
                .load(contactList[position].photoURI)
                .apply(
                    options.centerCrop()
                        .skipMemoryCache(true)
                        .priority(Priority.HIGH)
                        .format(DecodeFormat.PREFER_ARGB_8888)
                )
                .into(avatar)

            itemView.setOnClickListener {
                if (!selectedContactList.contains(contactList[position])) {
                    selectedContactList.add(contactList[position])
                    selection.setImageDrawable(context.resources.getDrawable(R.drawable.ic_check_radio))
                } else {
                    selectedContactList.remove(contactList[position])
                    selection.setImageDrawable(context.resources.getDrawable(R.drawable.ic_uncheck_radio))
                }


            }
        }
    }

    fun getList(): ArrayList<ContactModel> {
        return selectedContactList
    }
}