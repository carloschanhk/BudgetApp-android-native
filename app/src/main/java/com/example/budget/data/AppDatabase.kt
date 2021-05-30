package com.example.budget.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.budget.data.expense.Category
import com.example.budget.data.expense.ExpenseDao
import com.example.budget.data.expense.Transaction

@Database(entities = [Transaction::class,Category::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun ExpenseDao(): ExpenseDao
}