package com.example.budget.ui.spending_details

import android.annotation.SuppressLint
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.budget.R
import com.example.budget.data.expense.Transaction
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat", "WeekBasedYear")
@BindingAdapter("date")
fun bindDateToText(textView: TextView, date: Date) {
    textView.text = SimpleDateFormat("dd-MMM-YYYY").format(date)
}

@BindingAdapter("spendingDetailsData")
fun bindRecyclerViewData(
    recyclerView: RecyclerView,
    data: List<Transaction>
) {
    val adapter = recyclerView.adapter as SpendingTransactionAdapter
    adapter.submitList(data)
}

@BindingAdapter("transactionCost")
fun bindMoneyAmountToText(textView: TextView, cost: Float) {
    textView.text = textView.resources.getString(R.string.money_amount, cost.toInt())
}

