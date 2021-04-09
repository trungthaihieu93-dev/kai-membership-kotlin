package com.kardia.membership.features.fragments.select_account

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kardia.membership.R
import com.kardia.membership.core.extension.loadFromAnyWithoutCache
import com.kardia.membership.core.extension.loadFromUrlRounded
import com.kardia.membership.core.platform.OnItemClickListener
import com.kardia.membership.data.entities.User
import javax.inject.Inject
import kotlin.properties.Delegates
import com.kardia.membership.features.fragments.select_account.SelectAccountAdapter.SelectAccountViewHolder
import kotlinx.android.synthetic.main.item_select_account.view.*

class SelectAccountAdapter @Inject constructor() :
    RecyclerView.Adapter<SelectAccountViewHolder>() {

    var onItemClickListener: OnItemClickListener? = null

    internal var collection: ArrayList<User> by Delegates.observable(ArrayList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    inner class SelectAccountViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            //set width and height for item view
        }

        fun bind(position: Int) {
            val item = collection[position]
            itemView.ivPhotoUser.loadFromUrlRounded(item.avatar,6f)
            itemView.tvNameUser.text = item.email
            itemView.tvEmailUser.text = item.email
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
    ): SelectAccountViewHolder {
        return SelectAccountViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_select_account, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return collection.size
    }

    override fun onBindViewHolder(holder: SelectAccountViewHolder, position: Int) {
        holder.bind(position)
    }

}