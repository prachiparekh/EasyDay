package com.app.easyday.utils.camera_utils

import androidx.recyclerview.widget.DiffUtil
import com.app.easyday.app.sources.local.model.Media

class MediaDiffCallback : DiffUtil.ItemCallback<Media>() {
    override fun areItemsTheSame(oldItem: Media, newItem: Media): Boolean = oldItem.uri == newItem.uri

    override fun areContentsTheSame(oldItem: Media, newItem: Media): Boolean = oldItem == newItem
}
