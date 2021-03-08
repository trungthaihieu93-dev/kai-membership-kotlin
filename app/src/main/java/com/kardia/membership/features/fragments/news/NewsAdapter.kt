package com.kardia.membership.features.fragments.news

import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kardia.membership.R
import com.kardia.membership.core.platform.OnItemClickListener
import com.kardia.membership.data.entities.News
import javax.inject.Inject
import kotlin.properties.Delegates
import com.kardia.membership.features.fragments.news.NewsAdapter.NewsViewHolder

class NewsAdapter
@Inject constructor() :
    RecyclerView.Adapter<NewsViewHolder>() {

    var onItemClickListener: OnItemClickListener? = null

    var itemSize =
        (Resources.getSystem().displayMetrics.widthPixels / 3) * 2

    internal var collection: ArrayList<News> by Delegates.observable(ArrayList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    inner class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
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
    ): NewsViewHolder {
        return NewsViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_news, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return 3
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(position)
    }

}