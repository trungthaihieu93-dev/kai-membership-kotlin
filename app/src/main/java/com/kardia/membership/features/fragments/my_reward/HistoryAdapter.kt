package com.kardia.membership.features.fragments.my_reward

import android.content.Context
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.text.color
import androidx.recyclerview.widget.RecyclerView
import com.kardia.membership.R
import com.kardia.membership.core.extension.changeDateFormat
import com.kardia.membership.core.extension.visible
import com.kardia.membership.data.entities.History
import javax.inject.Inject
import kotlin.properties.Delegates
import com.kardia.membership.features.fragments.my_reward.HistoryAdapter.HistoryViewHolder
import kotlinx.android.synthetic.main.item_history.view.*

class HistoryAdapter
@Inject constructor(val context: Context) :
    RecyclerView.Adapter<HistoryViewHolder>() {

    internal var collection: ArrayList<History> by Delegates.observable(ArrayList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    inner class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            //set width and height for item view
        }

        fun bind(position: Int) {
            val item = collection[position]
            itemView.tvNameHistory.text = item.rewardName
            val myCustomizedString = SpannableStringBuilder()
                .color(ContextCompat.getColor(context,R.color.color_98A1B1)) {
                    append(context.getString(R.string.received_on))
                }
                .append(" ")
                .append(item.createdDate?.changeDateFormat("dd/MM/yyyy"))
            itemView.tvDescHistory.text = myCustomizedString
            item.codeCard?.let{
                if(it.isNotEmpty()){
                    itemView.tvDescHistory.visible()
                    val myCustomizedStringCode = SpannableStringBuilder()
                        .color(ContextCompat.getColor(context,R.color.color_98A1B1)) {
                            append(context.getString(R.string.code))
                        }
                        .append(" ")
                        .append(it)
                    itemView.tvDescHistory.text = myCustomizedStringCode
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HistoryViewHolder {
        return HistoryViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_history, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return collection.size
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(position)
    }
}