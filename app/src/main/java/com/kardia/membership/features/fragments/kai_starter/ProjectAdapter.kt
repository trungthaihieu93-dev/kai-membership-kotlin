package com.kardia.membership.features.fragments.kai_starter

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kardia.membership.R
import com.kardia.membership.core.extension.px2dp
import com.kardia.membership.core.platform.OnItemClickListener
import com.kardia.membership.data.entities.Project
import javax.inject.Inject
import kotlin.collections.ArrayList
import kotlin.properties.Delegates
import com.kardia.membership.features.fragments.kai_starter.ProjectAdapter.ProjectViewHolder

class ProjectAdapter
@Inject constructor() :
    RecyclerView.Adapter<ProjectViewHolder>() {

    var onItemClickListener: OnItemClickListener? = null

    var itemSize =
        (Resources.getSystem().displayMetrics.widthPixels /10) *9

    internal var collection: ArrayList<Project> by Delegates.observable(ArrayList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    inner class ProjectViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            //set width and height for item view
            (itemView.layoutParams as ViewGroup.MarginLayoutParams).width = itemSize.toInt()
        }

        fun bind(position: Int) {
//            val item = collection[position]

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
    ): ProjectViewHolder {
        return ProjectViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_project, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return 3
    }

    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
        holder.bind(position)
    }

}