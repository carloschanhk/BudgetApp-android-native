package com.example.budget.data.expense

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {
    @Insert
    fun createTransaction(transaction: Transaction)

    @Delete
    fun deleteTransaction(transaction: Transaction)

    @Query("SELECT * FROM `transaction` WHERE category= :category")
    fun getTransactions(category: String): Flow<MutableList<Transaction>>

    @Query("SELECT * FROM `transaction`")
    fun getAllTransactions(): Flow<MutableList<Transaction>>

    @Update
    fun updateTransaction(transaction: Transaction)
}