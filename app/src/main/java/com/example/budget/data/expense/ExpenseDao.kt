package com.example.budget.data.expense

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {
//    @Transaction
//    @Query("SELECT * FROM CATEGORY WHERE categoryTitle= :categoryType")
//    fun getCategoryWithTransactions(categoryType: String):List<CategoryWithTransactions>
//
//    @Insert
//    fun createTransaction(transaction: com.example.budget.data.expense.Transaction)
//
//    @Insert
//    fun createCategory(category: Category)
//

//    @Query("SELECT EXISTS(SELECT * FROM Category WHERE categoryTitle= :title)")
//    fun isCategoryExists(title:String):Boolean
    @Insert
    fun createTransaction(transaction: com.example.budget.data.expense.Transaction)

    @Delete
    fun deleteTransaction(transaction: com.example.budget.data.expense.Transaction)

    @Query("SELECT * FROM `transaction` WHERE category= :category")
    fun getTransactions(category: String): Flow<List<Transaction>>
}