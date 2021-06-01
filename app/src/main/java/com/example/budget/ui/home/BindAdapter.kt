package com.example.budget.ui.home

import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.annotation.RequiresApi
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.budget.common.CategoryType
import com.example.budget.data.expense.CategoryWithTransactions

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
    Log.d("binding","$data")
    adapter?.submitList(data)
}
