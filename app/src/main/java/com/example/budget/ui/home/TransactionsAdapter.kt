package com.example.budget.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chauthai.swipereveallayout.SwipeRevealLayout
import com.chauthai.swipereveallayout.ViewBinderHelper
import com.example.budget.R
import com.example.budget.data.expense.Transaction
import com.example.budget.databinding.HomeItemSwipeRevealLayoutBinding


class TransactionsAdapter (private val homeViewModel: HomeViewModel, private val navController: NavController) :
    ListAdapter<Transaction, TransactionsAdapter.ItemViewHolder>(DiffCallback) {
    private val viewBinderHelper = ViewBinderHelper()
    companion object DiffCallback : DiffUtil.ItemCallback<Transaction>() {
        override fun areItemsTheSame(oldItem: Transaction, newItem: Transaction): Boolean =
            oldItem.date == newItem.date

        override fun areContentsTheSame(oldItem: Transaction, newItem: Transaction): Boolean =
            oldItem.title == newItem.title

    }

    class ItemViewHolder(val binding: HomeItemSwipeRevealLayoutBinding): RecyclerView.ViewHolder(binding.root){
        val swipeRevealLayout : SwipeRevealLayout = itemView.findViewById(R.id.swipe_reveal_layout)

        fun bind(transaction: Transaction, homeViewModel: HomeViewModel, navController: NavController){
            binding.transaction = transaction
            binding.viewModel = homeViewModel
            binding.navController = navController
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(HomeItemSwipeRevealLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val transaction = getItem(position)
        viewBinderHelper.bind(holder.swipeRevealLayout,transaction.transactionId.toString())
        holder.bind(transaction, homeViewModel, navController)
    }
}