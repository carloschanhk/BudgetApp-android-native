package com.example.budget.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.budget.common.CategoryType
import com.example.budget.databinding.HomeItemTransactionBinding

class TransactionItemAdapter(private val context: Context, private val dataset: Array<CategoryType>) : RecyclerView.Adapter<TransactionItemAdapter.ItemViewHolder>(){

    class ItemViewHolder(private val binding: HomeItemTransactionBinding, val context: Context): RecyclerView.ViewHolder(binding.root){
        fun bind(categoryType: CategoryType){
            binding.context = context
            binding.categoryType = categoryType
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(HomeItemTransactionBinding.inflate(LayoutInflater.from(parent.context)),parent.context)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val type = dataset[position]
        holder.bind(type)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}