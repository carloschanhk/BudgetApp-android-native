package com.example.budget.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.budget.R
import com.example.budget.common.CategoryType
import com.example.budget.data.expense.Transaction
import java.text.SimpleDateFormat
import java.util.*

//List Item
@SuppressLint("UseCompatLoadingForDrawables")
@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
@BindingAdapter(value = ["transactions", "context", "monthBudget", "type"], requireAll = true)
fun bindProgressBar(
    view: ProgressBar,
    transactions: List<Transaction>?,
    context: Context,
    budget: Int,
    type: CategoryType
) {
    view.progressDrawable = context.getDrawable(type.color)
    if (transactions != null) {

        var categoryCost = 0F
        for (item in transactions) {
            categoryCost += item.cost!!
        }
        if (budget == 0) {
            view.progress = 0
        } else {
            view.progress = (categoryCost / budget * 100).toInt()
        }
    }

}

@BindingAdapter("android:src")
fun bindImage(view: ImageView, drawableId: Int) {
    view.setImageResource(drawableId)
}

@BindingAdapter("categoryCost")
fun bindCostToText(textView: TextView, list: List<Transaction>?) {
    var sum = 0F
    if (list != null) {
        for (item in list) {
            sum += item.cost!!
        }
    }
    textView.text = textView.resources.getString(R.string.money_amount, sum.toInt())
}

@BindingAdapter("numOfTransactions")
fun bindNumToText(textView: TextView, list: List<Transaction>?) {
    if (list != null) {
        textView.text = textView.resources.getQuantityString(
            R.plurals.num_of_transactions,
            list.size,
            list.size
        )
    } else {
        textView.text = textView.resources.getString(R.string.transaction, 0)
    }
}

@BindingAdapter(value = ["transactions", "monthBudget"], requireAll = true)
fun bindPercentageToText(textView: TextView, list: List<Transaction>?, monthBudget: Int) {
    var categoryCost = 0F
    if (list != null) {
        for (item in list) {
            categoryCost += item.cost!!
        }
    }
    if (monthBudget > 0) {
        var percentage = (categoryCost / monthBudget * 100).toInt()
        textView.text = textView.resources.getString(R.string.budget_percentage, percentage)
    } else {
        textView.text = textView.resources.getString(R.string.budget_percentage, 0)
    }

}

//Month Budget Segment
@BindingAdapter(value = ["totalExpenses", "monthBudget"], requireAll = true)
fun bindMonthBudgetPercentage(textView: TextView, totalExpenses: Int, monthBudget: Int) {
    if (monthBudget > 0) {
        val percentage: Int = totalExpenses * 100 / monthBudget
        textView.text =
            textView.resources.getString(
                R.string.num_of_percent,
                percentage
            )
    } else {
        textView.text = textView.resources.getString(R.string.num_of_percent, 0)
    }
}

@BindingAdapter(value = ["totalExpenses", "monthBudget"], requireAll = true)
fun bindBudgetToProgressBar(progressBar: ProgressBar, totalExpenses: Int, monthBudget: Int) {
    if (monthBudget > 0) {
        progressBar.progress = totalExpenses * 100 / monthBudget
    } else {
        progressBar.progress = 0
    }
}

@BindingAdapter("monthBudget")
fun bindBudgetToText(textView: TextView, monthBudget: Int) {
    textView.text = textView.resources.getString(
        R.string.money_amount,
        (if (monthBudget > 0) monthBudget else 0)
    )
}

//RecyclerView Item
@BindingAdapter("listData")
fun bindRecyclerView(
    recyclerView: RecyclerView,
    data: List<Transaction>?
) {
    val adapter = recyclerView.adapter as TransactionsAdapter
    adapter.submitList(data)
}

@SuppressLint("SimpleDateFormat", "WeekBasedYear")
@BindingAdapter("date")
fun bindDateToText(textView: TextView,date: Date){
    textView.text = SimpleDateFormat("dd-MMM-YYYY").format(date)
}

@BindingAdapter("cost")
fun bindCostToText(textView: TextView, cost: Float){
    textView.text = textView.resources.getString(R.string.money_amount, cost.toInt())
}