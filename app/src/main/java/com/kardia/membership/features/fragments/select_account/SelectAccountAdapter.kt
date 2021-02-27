package com.kardia.membership.features.fragments.select_account

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kardia.membership.R
import com.kardia.membership.core.platform.OnItemClickListener
import com.kardia.membership.data.entities.Account
import javax.inject.Inject
import kotlin.properties.Delegates
import com.kardia.membership.features.fragments.select_account.SelectAccountAdapter.SelectAccountViewHolder

class SelectAccountAdapter @Inject constructor() :
    RecyclerView.Adapter<SelectAccountViewHolder>() {

    var onItemClickListener: OnItemClickListener? = null

    internal var collection: ArrayList<Account> by Delegates.observable(ArrayList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    inner class SelectAccountViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            //set width and height for item view
        }

        fun bind(position: Int) {
//            val item = collection[position]

            itemView.setOnClickListener {
                onItemClickListener?.onItemClick(
                    0,
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
        return 2
    }

    override fun onBindViewHolder(holder: SelectAccountViewHolder, position: Int) {
        holder.bind(position)
    }

}