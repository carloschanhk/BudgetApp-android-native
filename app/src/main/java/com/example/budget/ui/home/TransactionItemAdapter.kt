package com.example.budget.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.budget.common.CategoryType
import com.example.budget.data.expense.Transaction
import com.example.budget.databinding.HomeItemTransactionBinding

class TransactionItemAdapter:
    ListAdapter<Triple<List<Transaction>,Int,CategoryType>, TransactionItemAdapter.ItemViewHolder>(DiffCallback){

    companion object DiffCallback : DiffUtil.ItemCallback<Triple<List<Transaction>,Int,CategoryType>>(){
        override fun areItemsTheSame(
            oldItem: Triple<List<Transaction>,Int,CategoryType>,
            newItem: Triple<List<Transaction>,Int,CategoryType>
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: Triple<List<Transaction>,Int,CategoryType>,
            newItem: Triple<List<Transaction>,Int,CategoryType>
        ): Boolean {
            return oldItem == newItem
        }
    }

    class ItemViewHolder(private val binding: HomeItemTransactionBinding, val context: Context): RecyclerView.ViewHolder(binding.root){
        fun bind(tripleItem: Triple<List<Transaction>,Int,CategoryType>){
            binding.context = context
            binding.transactions = tripleItem.first
            binding.monthBudget = tripleItem.second
            binding.categoryType = tripleItem.third
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