package com.kardia.membership.features.fragments.top_up

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kardia.membership.R
import com.kardia.membership.core.extension.formatPrice
import com.kardia.membership.core.platform.OnItemClickListener
import com.kardia.membership.data.entities.TopUpAmount
import javax.inject.Inject
import kotlin.properties.Delegates
import com.kardia.membership.features.fragments.top_up.TopUpAmountAdapter.TopUpAmountViewHolder
import kotlinx.android.synthetic.main.item_top_up_amount.view.*

class TopUpAmountAdapter
@Inject constructor() :
    RecyclerView.Adapter<TopUpAmountViewHolder>() {

    var onItemClickListener: OnItemClickListener? = null

    internal var collection: ArrayList<TopUpAmount> by Delegates.observable(ArrayList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    inner class TopUpAmountViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            //set width and height for item view
        }

        fun bind(position: Int) {
            val item = collection[position]
            if (item.isSelected) {
                itemView.tvPriceValue.alpha = 1f
                itemView.tvKAIValue.alpha = 1f
            } else {
                itemView.tvPriceValue.alpha = 0.3f
                itemView.tvKAIValue.alpha = 0.3f
            }
            itemView.tvPriceValue.text = item.priceValue?.formatPrice()
            itemView.tvKAIValue.text = item.KAIValue
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
    ): TopUpAmountViewHolder {
        return TopUpAmountViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_top_up_amount, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return collection.size
    }

    override fun onBindViewHolder(holder: TopUpAmountViewHolder, position: Int) {
        holder.bind(position)
    }

}