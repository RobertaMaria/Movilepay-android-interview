package com.roberta.movilepay_android_interview.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.roberta.movilepay_android_interview.R
import com.roberta.movilepay_android_interview.model.detailaccount.TransactionAccount

class TransactionAdapter :
    ListAdapter<TransactionAccount, TransactionAdapter.MyViewHolder>(differCallbackTransaction) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflate = LayoutInflater.from(parent.context)
            .inflate(R.layout.transaction_item, parent, false)
        return MyViewHolder(inflate)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemCount(): Int = currentList.size

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val fieldLabel by lazy { itemView.findViewById<TextView>(R.id.tranaction_item_label) }
        private val fieldDescriptionl by lazy { itemView.findViewById<TextView>(R.id.tranaction_item_description) }
        private val fieldValue by lazy { itemView.findViewById<TextView>(R.id.tranaction_item_value) }

        fun bind(transaction: TransactionAccount) {
            fieldDescriptionl.text = transaction.description
            fieldLabel.text = transaction.label
            fieldValue.text = transaction.value
        }
    }
}

val differCallbackTransaction = object : DiffUtil.ItemCallback<TransactionAccount>() {
    override fun areItemsTheSame(
        oldItem: TransactionAccount,
        newItem: TransactionAccount
    ): Boolean {
        return oldItem.label == newItem.label
    }

    override fun areContentsTheSame(
        oldItem: TransactionAccount,
        newItem: TransactionAccount
    ): Boolean {
        return oldItem == newItem
    }
}