package com.example.budget.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.budget.data.expense.CategoryWithTransactions
import com.example.budget.databinding.HomeItemTransactionBinding

class TransactionItemAdapter:
    ListAdapter<Pair<List<CategoryWithTransactions>,Int>, TransactionItemAdapter.ItemViewHolder>(DiffCallback){

    companion object DiffCallback : DiffUtil.ItemCallback<Pair<List<CategoryWithTransactions>,Int>>(){
        override fun areItemsTheSame(
            oldItem: Pair<List<CategoryWithTransactions>,Int>,
            newItem: Pair<List<CategoryWithTransactions>,Int>
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: Pair<List<CategoryWithTransactions>,Int>,
            newItem: Pair<List<CategoryWithTransactions>,Int>
        ): Boolean {
            return oldItem == newItem
        }
    }

    class ItemViewHolder(private val binding: HomeItemTransactionBinding, val context: Context): RecyclerView.ViewHolder(binding.root){
        fun bind(pairItem: Pair<List<CategoryWithTransactions>,Int>){
            binding.context = context
            binding.catWithTransactions = pairItem.first
            binding.monthBudget = pairItem.second
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionItemAdapter.ItemViewHolder {
        return ItemViewHolder(HomeItemTransactionBinding.inflate(LayoutInflater.from(parent.context)),parent.context)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val type = getItem(position)
        holder.bind(type)
    }
}