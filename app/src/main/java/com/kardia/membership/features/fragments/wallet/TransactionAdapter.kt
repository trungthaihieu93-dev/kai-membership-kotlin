package com.kardia.membership.features.fragments.wallet

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kardia.membership.R
import com.kardia.membership.core.extension.loadFromUrlRounded
import com.kardia.membership.core.platform.OnItemClickListener
import com.kardia.membership.data.entities.Transaction
import com.kardia.membership.data.entities.TransactionItem
import com.kardia.membership.data.entities.User
import kotlinx.android.synthetic.main.item_select_account.view.*
import javax.inject.Inject
import kotlin.properties.Delegates
import com.kardia.membership.features.fragments.wallet.TransactionAdapter.TransactionViewHolder
import kotlinx.android.synthetic.main.item_transaction.view.*

class TransactionAdapter
@Inject constructor(val transactionItemAdapter: TransactionItemAdapter) :
    RecyclerView.Adapter<TransactionViewHolder>() {

    internal var collection: ArrayList<Transaction> by Delegates.observable(ArrayList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    inner class TransactionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            //set width and height for item view
        }

        fun bind(position: Int) {
            val item = collection[position]
            transactionItemAdapter.collection = item.transactionItems as ArrayList<TransactionItem>
            itemView.rvTransactionItem.adapter = transactionItemAdapter
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TransactionViewHolder {
        return TransactionViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_transaction, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return collection.size
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        holder.bind(position)
    }

}