package com.example.budget.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.budget.data.expense.Transaction
import com.example.budget.databinding.HomeItemTransactionBinding

class TransactionsAdapter :
    ListAdapter<Transaction, TransactionsAdapter.ItemViewHolder>(DiffCallback) {
    companion object DiffCallback : DiffUtil.ItemCallback<Transaction>() {
        override fun areItemsTheSame(oldItem: Transaction, newItem: Transaction): Boolean =
            oldItem.date == newItem.date

        override fun areContentsTheSame(oldItem: Transaction, newItem: Transaction): Boolean =
            oldItem.title == newItem.title

    }

    class ItemViewHolder(val binding: HomeItemTransactionBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(transaction: Transaction){
            binding.transaction = transaction
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(HomeItemTransactionBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val transaction = getItem(position)
        holder.bind(transaction)
    }

}