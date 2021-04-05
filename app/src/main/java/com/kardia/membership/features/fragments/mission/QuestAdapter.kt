package com.kardia.membership.features.fragments.mission

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.kardia.membership.R
import com.kardia.membership.core.extension.gone
import com.kardia.membership.core.extension.visible
import com.kardia.membership.data.entities.Quest
import com.kardia.membership.features.fragments.mission.QuestAdapter.MissionViewHolder
import kotlinx.android.synthetic.main.item_quest.view.*
import javax.inject.Inject
import kotlin.properties.Delegates

class QuestAdapter
@Inject constructor(val context: Context) :
    RecyclerView.Adapter<MissionViewHolder>() {

    internal var collection: ArrayList<Quest> by Delegates.observable(ArrayList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    inner class MissionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            //set width and height for item view
        }

        fun bind(position: Int) {
            val item = collection[position]
            itemView.ivQuest.setImageResource(R.drawable.ic_rocket_quest)
            itemView.tvNameQuest.text = item.mission
            itemView.tvContentQuest.gone()
            itemView.rlProgressBarQuest.gone()
            itemView.tvContentQuest.setTextColor(
                ContextCompat.getColor(
                    context,
                    R.color.color_4E5D78
                )
            )
            if (item.process == item.processing) {
                itemView.tvContentQuest.visible()
                itemView.tvContentQuest.setTextColor(
                    ContextCompat.getColor(
                        context,
                        R.color.color_0E8C31
                    )
                )
                itemView.tvContentQuest.text = context.getString(R.string.completed)
                itemView.ivQuest.setImageResource(R.drawable.ic_check_quest)
            }else {
                item.process?.let { process ->
                    if (process == 0) {
                        itemView.tvContentQuest.visible()
                    } else {
                        itemView.rlProgressBarQuest.visible()
                        itemView.pbQuest.progress = 0
                        itemView.tvProgress.text =
                            String.format("%02d/%02d", 0, process)
                        item.processing?.let { processing ->
                            if(process<=processing){
                                itemView.rlProgressBarQuest.gone()
                                itemView.tvContentQuest.visible()
                                itemView.tvContentQuest.setTextColor(
                                    ContextCompat.getColor(
                                        context,
                                        R.color.color_0E8C31
                                    )
                                )
                                itemView.tvContentQuest.text = context.getString(R.string.completed)
                                itemView.ivQuest.setImageResource(R.drawable.ic_check_quest)
                            }
                            else {
                                itemView.pbQuest.progress = (processing * 100 / process)
                                itemView.tvProgress.text =
                                    String.format("%02d/%02d", processing, process)
                            }
                        }
                    }
                }
            }
//            when (item.key) {
//                AppConstants.KEY_QUEST_HIGHEST_SCORES -> {
//                    itemView.tvContentQuest.text = context.getString(
//                        R.string.content_top_score,
//                        item.process?.formatThousand(),
//                        item.description
//                    )
//                }
//                AppConstants.KEY_QUEST_THIRTY_MINUTES -> {
//
//                }
//                AppConstants.KEY_QUEST_INVITE_FRIEND -> {
//                }
//                AppConstants.KEY_QUEST_SIGN_IN -> {
//                }
//            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MissionViewHolder {
        return MissionViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_quest, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return collection.size
    }

    override fun onBindViewHolder(holder: MissionViewHolder, position: Int) {
        holder.bind(position)
    }

}