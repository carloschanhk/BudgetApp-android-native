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
import com.example.budget.common.CategoryType
import com.example.budget.data.expense.CategoryWithTransactions
import com.example.budget.data.expense.Transaction

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
@BindingAdapter(value=["type","context"],requireAll = true)
fun bindColor(view: ProgressBar, type: CategoryType, context: Context){
    view.progressDrawable = context.getDrawable(type.color)
}

@BindingAdapter("android:src")
fun bindImage(view: ImageView, drawableId: Int){
    view.setImageResource(drawableId)
}

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<List<CategoryWithTransactions>>?){
    val adapter = recyclerView.adapter as? TransactionItemAdapter
    adapter?.submitList(data)
}

@BindingAdapter("categoryCost")
fun bindCostToText(textView: TextView, list: List<Transaction>?){
    Log.d("bindAdapter","$list")
    var sum = 0
    if (list != null) {
        for (item in list){
            sum += item.cost!!
        }
    }
    textView.text = textView.resources.getString(R.string.money_amount,sum)
}
@BindingAdapter("numOfTransactions")
fun bindNumToText(textView: TextView, list: List<Transaction>?){
    if (list != null) {
        if (list.isEmpty()){
            textView.text = textView.resources.getString(R.string.transaction,0)
        } else {
            textView.text = textView.resources.getQuantityString(R.plurals.num_of_transactions, list.size,list.size)
        }
        Log.d("numtotext","${list.size}")
    }
}