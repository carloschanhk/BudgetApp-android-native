package com.example.budget.data.chart

import android.os.Build
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import java.time.LocalDate

@BindingAdapter("barChartData")
fun bindBarChartData(recyclerView: RecyclerView, data: List<BarChartEntry>?) {
    val adapter = recyclerView.adapter as BarChartAdapter
    data?.let { adapter.submitList(it) }
}

@RequiresApi(Build.VERSION_CODES.O)
@BindingAdapter("barEntryDate")
fun bindEntryDateToText(textView: TextView, date: LocalDate) {
    textView.text = date.dayOfMonth.toString()
}

@BindingAdapter("highestExpense")
fun bindHighestExpenseToLabel(textView: TextView, expense: Int?){
    expense?.let {
        textView.text = expense.toString()
    }
}