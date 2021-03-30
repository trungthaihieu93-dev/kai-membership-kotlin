package com.kardia.membership.features.fragments.news

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kardia.membership.R
import com.kardia.membership.core.extension.changeDateFormat
import com.kardia.membership.core.extension.loadFromUrlRounded
import com.kardia.membership.core.platform.OnItemClickListener
import com.kardia.membership.data.entities.NewsFeature
import javax.inject.Inject
import kotlin.properties.Delegates
import com.kardia.membership.features.fragments.news.NewsAdapter.NewsViewHolder
import kotlinx.android.synthetic.main.item_news.view.*
import java.util.*
import kotlin.collections.ArrayList

class NewsAdapter
@Inject constructor() :
    RecyclerView.Adapter<NewsViewHolder>() {

    var onItemClickListener: OnItemClickListener? = null

    var itemSize =
        (Resources.getSystem().displayMetrics.widthPixels / 3) * 2

    internal var collection: ArrayList<NewsFeature> by Delegates.observable(ArrayList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    inner class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            //set width and height for item view
            (itemView.layoutParams as ViewGroup.MarginLayoutParams).width = itemSize.toInt()
        }

        fun bind(position: Int) {
            val item = collection[position]
            itemView.ivThumbnailNews.loadFromUrlRounded(item.thumbnail,16f)
            itemView.tvTitleNews.text = item.title
            itemView.tvDateNews.text = item.pubDate?.changeDateFormat(
                "EEEE dd MMM yyyy",
                "yyyy-MM-dd HH:mm:ss",
                Locale.ENGLISH
            )
            itemView.setOnClickListener {
                onItemClickListener?.onItemClick(
                    item,
                    position
                )
            }
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
        return collection.size
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(position)
    }

}