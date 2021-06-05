package com.example.budget.ui.home

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
import com.example.budget.data.expense.CategoryWithTransactions
import com.example.budget.data.expense.Transaction

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
@BindingAdapter(value = ["catWithTransactions", "context", "monthBudget"], requireAll = true)
fun bindColor(
    view: ProgressBar,
    categoryWithTransactions: List<CategoryWithTransactions>?,
    context: Context,
    budget: Int
) {
    if (categoryWithTransactions != null && categoryWithTransactions.isNotEmpty()) {
        view.progressDrawable = context.getDrawable(categoryWithTransactions[0].category.type.color)
        var categoryCost = 0
        for (item in categoryWithTransactions[0].transaction) {
            categoryCost += item.cost!!
        }
        if (budget == 0) {
            view.progress = 0
        } else {
            view.progress = categoryCost / budget
        }
    } else {
        view.progressDrawable = context.getDrawable(R.drawable.progressbar_blank)
    }

}

@BindingAdapter("android:src")
fun bindImage(view: ImageView, drawableId: Int) {
    view.setImageResource(drawableId)
}

@BindingAdapter("listData")
fun bindRecyclerView(
    recyclerView: RecyclerView,
    data: List<Pair<List<CategoryWithTransactions>, Int>>?
) {
    val adapter = recyclerView.adapter as? TransactionItemAdapter
    adapter?.submitList(data)
}

@BindingAdapter("categoryCost")
fun bindCostToText(textView: TextView, list: List<Transaction>?) {
    Log.d("bindAdapter", "$list")
    var sum = 0
    if (list != null) {
        for (item in list) {
            sum += item.cost!!
        }
        textView.text = textView.resources.getString(R.string.money_amount, sum)
    }

}

@BindingAdapter("numOfTransactions")
fun bindNumToText(textView: TextView, list: List<Transaction>?) {
    if (list != null) {
        if (list.isEmpty()) {
            textView.text = textView.resources.getString(R.string.transaction, 0)
        } else {
            textView.text = textView.resources.getQuantityString(
                R.plurals.num_of_transactions,
                list.size,
                list.size
            )
        }
    }
}

@BindingAdapter("budgetPercentage")
fun bindPercentageToText(textView: TextView, list: List<Transaction>?) {
    if (list != null) {
        var categoryCost = 0
        for (item in list) {
            categoryCost += item.cost!!
        }
        textView.text = textView.resources.getString(R.string.budget_percentage, categoryCost)
    }
}

@BindingAdapter(value = ["totalExpenses","monthBudget"], requireAll = true)
fun bindMonthBudgetPercentage(textView: TextView, monthBudget: Int, totalExpenses:Int){
    if (monthBudget > 0){
        textView.text = textView.resources.getString(R.string.num_of_percent,(totalExpenses/monthBudget))
    } else {
        textView.text = textView.resources.getString(R.string.num_of_percent,0)
    }
}