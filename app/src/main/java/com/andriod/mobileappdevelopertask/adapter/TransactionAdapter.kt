package com.andriod.mobileappdevelopertask.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.andriod.mobileappdevelopertask.R
import com.andriod.mobileappdevelopertask.databinding.ItemTransactionBinding
import com.andriod.mobileappdevelopertask.entity.Transaction

class TransactionAdapter : RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TransactionAdapter.TransactionViewHolder {
        return TransactionViewHolder(
            ItemTransactionBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: TransactionAdapter.TransactionViewHolder, position: Int) {
        val currentTransaction = differ.currentList[position]

        if (currentTransaction.type == "Income") {
            holder.itemTransaction.typeTv.text = currentTransaction.type
            holder.itemTransaction.typeTv.setTextColor(
                ContextCompat.getColor(holder.itemView.context, R.color.income_color)
            )
        } else if (currentTransaction.type == "Expense") {
            holder.itemTransaction.typeTv.text = currentTransaction.type
            holder.itemTransaction.typeTv.setTextColor(
                ContextCompat.getColor(holder.itemView.context, R.color.expense_color)
            )
        }
        holder.itemTransaction.categoryTv.text = currentTransaction.category
        holder.itemTransaction.dateTv.text = currentTransaction.date
        holder.itemTransaction.descriptionTv.text = currentTransaction.description
        if (currentTransaction.type == "Income") {
            holder.itemTransaction.amountTv.text = "Rs. ${currentTransaction.amount}"
            holder.itemTransaction.amountTv.setTextColor(
                ContextCompat.getColor(holder.itemView.context, R.color.income_color)
            )
        } else if (currentTransaction.type == "Expense") {
            holder.itemTransaction.amountTv.text = "Rs. ${currentTransaction.amount}"
            holder.itemTransaction.amountTv.setTextColor(
                ContextCompat.getColor(holder.itemView.context, R.color.expense_color)
            )
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class TransactionViewHolder(val itemTransaction: ItemTransactionBinding) :
        RecyclerView.ViewHolder(itemTransaction.root)

    private val differCallBack = object : DiffUtil.ItemCallback<Transaction>() {
        override fun areItemsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
            return oldItem.id == newItem.id &&
                    oldItem.type == newItem.type &&
                    oldItem.category == newItem.category &&
                    oldItem.date == newItem.date
        }

        override fun areContentsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, differCallBack)
}