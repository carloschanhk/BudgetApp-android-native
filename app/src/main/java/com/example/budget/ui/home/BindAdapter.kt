package com.example.budget.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.budget.R
import com.example.budget.common.CategoryType
import com.example.budget.data.expense.Transaction

@BindingAdapter("listData")
fun bindRecyclerView(
    recyclerView: RecyclerView,
    data: List<Triple<List<Transaction>, Int, CategoryType>>?
) {
    val adapter = recyclerView.adapter as? TransactionItemAdapter
    adapter?.submitList(data)
}

//RecyclerView Item
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
            view.progress = (categoryCost / budget).toInt()
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
    textView.text = textView.resources.getString(R.string.money_amount, sum)
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

@BindingAdapter("budgetPercentage")
fun bindPercentageToText(textView: TextView, list: List<Transaction>?) {
    var categoryCost = 0F
    if (list != null) {
        for (item in list) {
            categoryCost += item.cost!!
        }
    }
    textView.text = textView.resources.getString(R.string.budget_percentage, categoryCost)
}

//Month Budget Segment
@BindingAdapter(value = ["totalExpenses", "monthBudget"], requireAll = true)
fun bindMonthBudgetPercentage(textView: TextView, monthBudget: Int, totalExpenses: Int) {
    if (monthBudget > 0) {
        textView.text =
            textView.resources.getString(R.string.num_of_percent, (totalExpenses / monthBudget))
    } else {
        textView.text = textView.resources.getString(R.string.num_of_percent, 0)
    }
}

@BindingAdapter(value = ["totalExpenses", "monthBudget"], requireAll = true)
fun bindBudgetToProgressBar(progressBar: ProgressBar, monthBudget: Int, totalExpenses: Int) {
    if (monthBudget > 0) {
        progressBar.progress = totalExpenses / monthBudget
    }
}