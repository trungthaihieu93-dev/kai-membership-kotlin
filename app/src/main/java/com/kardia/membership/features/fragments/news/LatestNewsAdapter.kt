package com.kardia.membership.features.fragments.news

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kardia.membership.R
import com.kardia.membership.core.extension.px2dp
import com.kardia.membership.core.platform.OnItemClickListener
import com.kardia.membership.data.entities.News
import javax.inject.Inject
import kotlin.properties.Delegates
import com.kardia.membership.features.fragments.news.LatestNewsAdapter.LatestNewsViewHolder

class LatestNewsAdapter
@Inject constructor() :
    RecyclerView.Adapter<LatestNewsViewHolder>() {

    var onItemClickListener: OnItemClickListener? = null

    var itemSize =
        (Resources.getSystem().displayMetrics.widthPixels / 14) * 13

    internal var collection: ArrayList<News> by Delegates.observable(ArrayList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    inner class LatestNewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            //set width and height for item view
            (itemView.layoutParams as ViewGroup.MarginLayoutParams).width = itemSize.toInt()
        }

        fun bind(position: Int) {
//            val item = collection[position]
//
//            itemView.setOnClickListener {
//                onItemClickListener?.onItemClick(
//                    item,
//                    position
//                )
//            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LatestNewsViewHolder {
        return LatestNewsViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_latest_news, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return 5
    }

    override fun onBindViewHolder(holder: LatestNewsViewHolder, position: Int) {
        holder.bind(position)
    }

}