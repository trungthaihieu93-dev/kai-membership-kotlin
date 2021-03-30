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
import com.kardia.membership.data.entities.NewsLatest
import javax.inject.Inject
import kotlin.properties.Delegates
import com.kardia.membership.features.fragments.news.LatestNewsAdapter.LatestNewsViewHolder
import kotlinx.android.synthetic.main.item_latest_news.view.*
import kotlinx.android.synthetic.main.item_latest_news.view.ivThumbnailNews
import kotlinx.android.synthetic.main.item_latest_news.view.tvDateNews
import kotlinx.android.synthetic.main.item_latest_news.view.tvTitleNews
import kotlinx.android.synthetic.main.item_news.view.*
import java.util.*
import kotlin.collections.ArrayList

class LatestNewsAdapter
@Inject constructor() :
    RecyclerView.Adapter<LatestNewsViewHolder>() {

    var onItemClickListener: OnItemClickListener? = null

    var itemSize =
        (Resources.getSystem().displayMetrics.widthPixels / 14) * 13

    internal var collection: ArrayList<NewsLatest> by Delegates.observable(ArrayList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    inner class LatestNewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            //set width and height for item view
            (itemView.layoutParams as ViewGroup.MarginLayoutParams).width = itemSize.toInt()
        }

        fun bind(position: Int) {
            val item = collection[position]
            item.entities?.media?.let { mediaList ->
                mediaList.forEach { media ->
                    media.media_url_https?.let { urlImage ->
                        itemView.ivThumbnailNews.loadFromUrlRounded(urlImage, 8f)
                        return@forEach
                    }
                }
            }
            itemView.tvTitleNews.text = item.full_text
            itemView.tvDateNews.text = item.created_at?.changeDateFormat(
                "EEE dd MMM yyyy",
                "EEE MMM dd HH:mm:ss Z yyyy",
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
    ): LatestNewsViewHolder {
        return LatestNewsViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_latest_news, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return collection.size
    }

    override fun onBindViewHolder(holder: LatestNewsViewHolder, position: Int) {
        holder.bind(position)
    }

}