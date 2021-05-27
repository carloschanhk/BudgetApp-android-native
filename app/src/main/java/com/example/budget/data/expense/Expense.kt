package com.example.budget.data.expense

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "expense")
data class Expense(
    val category: String?,
    val date: Date?,
    val cost: Int?,
    val title: String?,){
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null
}
