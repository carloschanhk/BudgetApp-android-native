package com.example.budget.data.chart

import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.budget.R
import com.example.budget.databinding.SpendingBarItemBinding

class BarChartAdapter(val context: Context) :
    ListAdapter<BarChartEntry, BarChartAdapter.BarChartEntryViewHolder>(DiffCallback) {
    companion object DiffCallback : DiffUtil.ItemCallback<BarChartEntry>() {
        override fun areItemsTheSame(oldItem: BarChartEntry, newItem: BarChartEntry): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: BarChartEntry, newItem: BarChartEntry): Boolean {
            return oldItem.date == newItem.date
        }
    }

    class BarChartEntryViewHolder(val binding: SpendingBarItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val resources: Resources = binding.barChartProgress.resources
        fun bind(barChartEntry: BarChartEntry, itemCount: Int) {
            binding.entry = barChartEntry
            binding.barChartProgress.layoutParams.width = if (itemCount == 7)
                resources.getDimension(R.dimen.bar_width_seven_days).toInt() else resources.getDimension(R.dimen.bar_width_30_days).toInt()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BarChartEntryViewHolder {
        return BarChartEntryViewHolder(
            SpendingBarItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: BarChartEntryViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, itemCount)
    }
}