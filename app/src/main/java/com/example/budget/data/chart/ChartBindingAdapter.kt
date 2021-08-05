package com.example.budget.data.chart

import android.os.Build
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.budget.R
import java.text.DecimalFormat
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.*

@BindingAdapter("barChartData")
fun bindBarChartData(recyclerView: RecyclerView, data: List<BarChartEntry>?) {
    val adapter = recyclerView.adapter as BarChartAdapter
    data?.let { adapter.submitList(it) }
}

@RequiresApi(Build.VERSION_CODES.O)
@BindingAdapter(value = ["barEntryDate", "itemCount"], requireAll = true)
fun bindEntryDateToText(textView: TextView, date: LocalDate, itemCount: Int) {
    if (itemCount == 7) {
        textView.text = date.dayOfWeek.getDisplayName(TextStyle.NARROW, Locale.ENGLISH)
    } else {
        textView.text =
            textView.resources.getString(R.string.chart_date, date.dayOfMonth, date.monthValue)
    }
}

@BindingAdapter("highestExpense")
fun bindHighestExpenseToLabel(textView: TextView, expense: Int?) {
    val roundedExpense: String? = expense?.let {
        when (it.toString().length){
            0, 1, 2, 3 -> it.toString()
            4, 5, 6 -> DecimalFormat("#.#").format(it.toFloat()/1000)+"K"
            7, 8, 9 -> DecimalFormat("#.#").format(it.toFloat()/1_000_000)+"M"
            else -> DecimalFormat("#.#").format(it.toFloat()/1_000_000_000)+"B"
        }
    }
    roundedExpense?.let {
        textView.text = it
    }
}