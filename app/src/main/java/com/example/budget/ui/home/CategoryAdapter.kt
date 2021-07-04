package com.example.budget.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.budget.common.CategoryType
import com.example.budget.data.expense.Transaction
import com.example.budget.databinding.HomeItemCategoryBinding

class CategoryAdapter(
    private val fragmentContext: Context,
    private val homeViewModel: HomeViewModel
) : ListAdapter<Pair<CategoryType, List<Transaction>?>, CategoryAdapter.CategoryViewHolder>(
    DiffCallback
) {
    companion object DiffCallback : DiffUtil.ItemCallback<Pair<CategoryType, List<Transaction>?>>() {
        override fun areItemsTheSame(
            oldItem: Pair<CategoryType, List<Transaction>?>,
            newItem: Pair<CategoryType, List<Transaction>?>
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: Pair<CategoryType, List<Transaction>?>,
            newItem: Pair<CategoryType, List<Transaction>?>
        ): Boolean {
            return oldItem.first == newItem.first
        }
    }

    class CategoryViewHolder(val binding: HomeItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            transactions: List<Transaction>?,
            categoryType: CategoryType,
            homeViewModel: HomeViewModel,
            fragmentContext: Context
        ) {
            binding.apply {
                context = fragmentContext
                this.transactions = transactions
                this.categoryType = categoryType
                this.viewModel = homeViewModel
            }
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(
            HomeItemCategoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val pair = getItem(position)
        holder.bind(pair.second,pair.first,homeViewModel,fragmentContext)
    }
}