package com.example.budget.ui.home

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.budget.common.CategoryType
import com.example.budget.data.expense.CategoryWithTransactions
import com.example.budget.databinding.HomeItemTransactionBinding

class TransactionItemAdapter:
    ListAdapter<List<CategoryWithTransactions>, TransactionItemAdapter.ItemViewHolder>(DiffCallback){

    companion object DiffCallback : DiffUtil.ItemCallback<List<CategoryWithTransactions>>(){
        override fun areItemsTheSame(
            oldItem: List<CategoryWithTransactions>,
            newItem: List<CategoryWithTransactions>
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: List<CategoryWithTransactions>,
            newItem: List<CategoryWithTransactions>
        ): Boolean {
            return oldItem[0].category.type == newItem[0].category.type
        }
    }

    class ItemViewHolder(private val binding: HomeItemTransactionBinding, val context: Context): RecyclerView.ViewHolder(binding.root){
        fun bind(categoryType: CategoryType){
            binding.context = context
            binding.categoryType = categoryType
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionItemAdapter.ItemViewHolder {
        return ItemViewHolder(HomeItemTransactionBinding.inflate(LayoutInflater.from(parent.context)),parent.context)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val type = getItem(position)[0].category.type
        Log.d("in binding viewholder", "$type")
        holder.bind(type)
    }
}