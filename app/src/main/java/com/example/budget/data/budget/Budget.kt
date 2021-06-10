package com.example.budget.data.budget

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "monthBudget")
data class MonthBudget(
    @PrimaryKey
    val month: String,
    val budget: Int,
)