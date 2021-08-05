package com.example.budget.data.expense

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {
    @Insert
    fun createTransaction(transaction: Transaction)

    @Delete
    fun deleteTransaction(transaction: Transaction)

    @Query("SELECT * FROM `transaction` WHERE category= :category ORDER BY date ASC")
    fun getTransactions(category: String): List<Transaction>

    @Query("SELECT * FROM `transaction` ORDER BY date ASC")
    fun getAllTransactions(): Flow<MutableList<Transaction>>

    @Update
    fun updateTransaction(transaction: Transaction)
}