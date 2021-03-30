package com.kardia.membership.features.fragments.wallet

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kardia.membership.R
import com.kardia.membership.core.extension.formatThousand
import com.kardia.membership.core.extension.changeDateFormat
import com.kardia.membership.data.entities.TransactionItem
import javax.inject.Inject
import kotlin.properties.Delegates
import com.kardia.membership.features.fragments.wallet.TransactionItemAdapter.TransactionItemViewHolder
import com.kardia.membership.features.utils.AppConstants
import kotlinx.android.synthetic.main.item_transaction_item.view.*

class TransactionItemAdapter
@Inject constructor(val context: Context) :
    RecyclerView.Adapter<TransactionItemViewHolder>() {

    internal var collection: ArrayList<TransactionItem> by Delegates.observable(ArrayList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    inner class TransactionItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            //set width and height for item view
        }

        fun bind(position: Int) {
            val item = collection[position]
            itemView.tvTypeTransaction.text = item.type?.toLowerCase()?.capitalize()
            when(item.type){
                AppConstants.TYPE_GET_TRANSACTION->{
                    itemView.tvWalletAddressTransaction.text = context.getString(R.string.wallet_address_from,item.wallet_send)
                    itemView.tvValueTransaction.text = String.format("+ %s",item.value?.formatThousand())
                    itemView.ivTypeTransaction.setImageResource(R.drawable.ic_type_get_transaction)
                }
                AppConstants.TYPE_BUY_TRANSACTION->{
                    itemView.tvWalletAddressTransaction.text = context.getString(R.string.wallet_address_from,item.wallet_send)
                    itemView.tvValueTransaction.text = String.format("+ %s",item.value?.formatThousand())
                    itemView.ivTypeTransaction.setImageResource(R.drawable.ic_type_buy_transaction)
                }
                AppConstants.TYPE_SEND_TRANSACTION->{
                    itemView.tvWalletAddressTransaction.text = context.getString(R.string.wallet_address_to,item.wallet_receive)
                    itemView.tvValueTransaction.text = String.format("- %s",item.value?.formatThousand())
                    itemView.ivTypeTransaction.setImageResource(R.drawable.ic_type_send_transaction)
                }
            }
            itemView.tvTimeTransaction.text = item.createdDate?.changeDateFormat(
                "hh:mm aa"
            )
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TransactionItemViewHolder {
        return TransactionItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_transaction_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return collection.size
    }

    override fun onBindViewHolder(holder: TransactionItemViewHolder, position: Int) {
        holder.bind(position)
    }

}