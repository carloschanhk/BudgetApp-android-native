package com.example.budget.data.chart

import android.content.res.Resources
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.budget.R
import com.example.budget.databinding.SpendingBarItemBinding

class BarChartAdapter :
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

        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(barChartEntry: BarChartEntry, count: Int, position: Int) {
            val showedPosition = listOf<Int>(0,4,9,14,19,24,29)
            binding.apply {
                entry = barChartEntry
                itemCount = count
                barChartProgress.layoutParams.width = if (itemCount == 7)
                    resources.getDimension(R.dimen.bar_width_seven_days)
                        .toInt() else resources.getDimension(R.dimen.bar_width_30_days).toInt()
                xAxisLabels.visibility = if (itemCount != 7 && showedPosition.indexOf(position) == -1) View.INVISIBLE else View.VISIBLE
            }
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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: BarChartEntryViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, itemCount, position)
    }
}