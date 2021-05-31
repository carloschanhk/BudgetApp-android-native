package com.example.budget.ui.home

import android.content.Context
import android.os.Build
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.annotation.RequiresApi
import androidx.databinding.BindingAdapter

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
@BindingAdapter(value=["progressColor","context"],requireAll = true)
fun bindColor(view: ProgressBar, drawableId: Int, context: Context){
    view.progressDrawable = context.getDrawable(drawableId)
}

@BindingAdapter("android:src")
fun bindImage(view: ImageView, drawableId: Int){
    view.setImageResource(drawableId)
}