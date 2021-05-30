package com.example.budget.data.expense

import androidx.room.*
import androidx.room.Transaction

@Dao
interface ExpenseDao {
    @Transaction
    @Query("SELECT * FROM CATEGORY WHERE categoryTitle= :categoryType")
    fun getCategoryWithTransactions(categoryType:String):List<CategoryWithTransactions>

    @Insert
    fun createTransaction(transaction: com.example.budget.data.expense.Transaction)

    @Insert
    fun createCategory(category: Category)

    @Query("SELECT EXISTS(SELECT * FROM Category WHERE categoryTitle= :title)")
    fun isCategoryExists(title:String):Boolean

    @Delete
    fun deleteTransaction(transaction: com.example.budget.data.expense.Transaction)
}