package com.example.budget.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.budget.data.budget.BudgetDao
import com.example.budget.data.budget.MonthBudget
import com.example.budget.data.expense.ExpenseDao
import com.example.budget.data.expense.Transaction

@Database(entities = [Transaction::class, MonthBudget::class], version = 1)
@TypeConverters(DBConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun ExpenseDao(): ExpenseDao
    abstract fun BudgetDao(): BudgetDao
}