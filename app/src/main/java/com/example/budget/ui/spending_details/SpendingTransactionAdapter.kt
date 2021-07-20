package com.example.budget.ui.spending_details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.budget.data.expense.Transaction
import com.example.budget.databinding.SpendingItemTransactionBinding

class SpendingTransactionAdapter() :
    ListAdapter<Transaction, SpendingTransactionAdapter.SpendingTransactionViewHolder>(
        DiffCallback
    ) {
    companion object DiffCallback : DiffUtil.ItemCallback<Transaction>() {
        override fun areItemsTheSame(
            oldItem: Transaction,
            newItem: Transaction
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: Transaction,
            newItem: Transaction
        ): Boolean {
            return oldItem.title == newItem.title
        }

    }


    class SpendingTransactionViewHolder(val binding: SpendingItemTransactionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(transaction: Transaction){
            binding.transaction = transaction
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SpendingTransactionViewHolder {
        return SpendingTransactionViewHolder(
            SpendingItemTransactionBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ),
                parent, false,
            )
        )
    }

    override fun onBindViewHolder(holder: SpendingTransactionViewHolder, position: Int) {
        val transaction = getItem(position)
        holder.bind(transaction)
    }
}