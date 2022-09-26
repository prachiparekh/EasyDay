package com.app.easyday.screens.activities.main.home.task

import android.content.Context
import android.graphics.Bitmap
import android.media.ThumbnailUtils
import android.provider.MediaStore.Video.Thumbnails.MINI_KIND
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.easyday.R
import com.app.easyday.app.sources.local.model.Media
import com.app.easyday.utils.camera_utils.MediaDiffCallback
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target

class BottomImageAdapter(
    val mContext: Context,
    private val mediaList: ArrayList<Media>,
    private val onItemClick: (Int, Media) -> Unit,
) : ListAdapter<Media, BottomImageAdapter.PicturesViewHolder>(MediaDiffCallback()) {

    var loadFail=false
    private val inflater: LayoutInflater = LayoutInflater.from(mContext)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PicturesViewHolder(inflater.inflate(R.layout.item_bottom_image, parent, false))

    override fun getItemCount(): Int = mediaList.size

    override fun onBindViewHolder(holder: PicturesViewHolder, position: Int) {
        holder.bind(mediaList[position])
    }

    inner class PicturesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imagePreview: ImageView = itemView.findViewById(R.id.img)
        private val imagePlay: ImageView = itemView.findViewById(R.id.imagePlay)

        fun bind(item: Media) {
            imagePlay.visibility = if (item.isVideo) View.VISIBLE else View.GONE

            val options = RequestOptions()
            imagePreview.clipToOutline = true
            if (position == 0) {
                imagePreview.setImageDrawable(mContext.resources.getDrawable(R.drawable.ic_square_add))
            } else {

                if (!item.isVideo) {
                    Glide.with(mContext)
                        .load(item.uri)
                        .apply(
                            options.centerCrop()
                                .skipMemoryCache(true)
                                .priority(Priority.HIGH)
                                .format(DecodeFormat.PREFER_ARGB_8888)
                        )
                        .into(imagePreview)
                } else {
                    Glide.with(mContext)
                        .asBitmap()
                        .load(item.uri)
                        .apply(
                            options.centerCrop()
                                .skipMemoryCache(true)
                                .priority(Priority.HIGH)
                                .format(DecodeFormat.PREFER_ARGB_8888)
                        ).listener(
                            object : RequestListener<Bitmap> {

                                override fun onResourceReady(
                                    resource: Bitmap?,
                                    model: Any?,
                                    target: com.bumptech.glide.request.target.Target<Bitmap>?,
                                    dataSource: DataSource?,
                                    isFirstResource: Boolean
                                ): Boolean {
                                    return false
                                }

                                override fun onLoadFailed(
                                    e: GlideException?,
                                    model: Any?,
                                    target: Target<Bitmap>?,
                                    isFirstResource: Boolean
                                ): Boolean {
                                    loadFail=true
                                    notifyItemChanged(adapterPosition)
                                    return false
                                }

                            }
                        )
                        .into(imagePreview)
                }
            }

            if(loadFail){
                Glide.with(mContext)
                    .load(item.uri?.let {
                        getThumbnailImage(
                            it.toString()
                        )
                    })
                    .apply(
                        options.centerCrop()
                            .skipMemoryCache(true)
                            .priority(Priority.HIGH)
                            .format(DecodeFormat.PREFER_ARGB_8888)
                    )
                    .into(imagePreview)
            }

            imagePreview.setOnClickListener {
                onItemClick(
                    adapterPosition, item
                )
            }
        }
    }

    fun getThumbnailImage(videoPath: String?): Bitmap? {
        return videoPath?.let { ThumbnailUtils.createVideoThumbnail(it, MINI_KIND) }
    }
}
