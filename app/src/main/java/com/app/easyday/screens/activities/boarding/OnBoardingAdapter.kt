package com.app.easyday.screens.activities.boarding

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.app.easyday.R
import com.app.easyday.app.sources.local.model.OnboardingItem

class OnBoardingAdapter(
    private var items: List<OnboardingItem> = arrayListOf()

) : RecyclerView.Adapter<OnBoardingAdapter.OnBoardingViewHolder>() {

    var anInterface: BoardingInterface? = null

    interface BoardingInterface {
        fun onClickNext(nextPosition: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = OnBoardingViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_onboarding_pager, parent, false)
    )

    override fun onBindViewHolder(holder: OnBoardingViewHolder, position: Int) {
        val item = items[position]
        val context = holder.itemView.context

        holder.title.text = context.getString(item.titleResId)
        holder.image.setImageDrawable(ContextCompat.getDrawable(context, item.imageResId))

        if (position == items.size - 1)
            holder.cta.text = context.getString(R.string.get_started)
        else
            holder.cta.text = context.getString(R.string.next)

        when (position) {
            0 -> {
                holder.image1.setImageDrawable(context.resources.getDrawable(R.drawable.ic_boarding_dash))
                holder.image2.setImageDrawable(context.resources.getDrawable(R.drawable.ic_bording_dot))
                holder.image3.setImageDrawable(context.resources.getDrawable(R.drawable.ic_bording_dot))
            }
            1 -> {
                holder.image1.setImageDrawable(context.resources.getDrawable(R.drawable.ic_bording_dot))
                holder.image2.setImageDrawable(context.resources.getDrawable(R.drawable.ic_boarding_dash))
                holder.image3.setImageDrawable(context.resources.getDrawable(R.drawable.ic_bording_dot))
            }
            2 -> {
                holder.image1.setImageDrawable(context.resources.getDrawable(R.drawable.ic_bording_dot))
                holder.image2.setImageDrawable(context.resources.getDrawable(R.drawable.ic_bording_dot))
                holder.image3.setImageDrawable(context.resources.getDrawable(R.drawable.ic_boarding_dash))
            }
        }

        holder.cta.setOnClickListener {
            anInterface?.onClickNext(position + 1)
        }

    }

    override fun getItemCount() = items.size

    class OnBoardingViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.title)
        val cta: TextView = view.findViewById(R.id.cta)
        val image: ImageView = view.findViewById(R.id.img)
        val image1: ImageView = view.findViewById(R.id.img1)
        val image2: ImageView = view.findViewById(R.id.img2)
        val image3: ImageView = view.findViewById(R.id.img3)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(items: List<OnboardingItem>, anInterface: BoardingInterface) {
        this.items = items
        this.anInterface = anInterface
        notifyDataSetChanged()
    }
}