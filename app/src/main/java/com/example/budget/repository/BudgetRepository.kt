package com.example.budget.repository

import com.example.budget.data.budget.BudgetDao
import com.example.budget.data.budget.MonthBudget
import com.example.budget.data.expense.ExpenseDao
import com.example.budget.data.expense.Transaction
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BudgetRepository @Inject constructor(private val expenseDao: ExpenseDao, private val budgetDao: BudgetDao) {
    fun getMonthBudget(month: String): Flow<Int>{
        return budgetDao.getMonthBudget(month)
    }

    suspend fun createTransaction(transaction: Transaction){
        expenseDao.createTransaction(transaction)
    }

    fun getTransactions(category: String): List<Transaction> {
        return expenseDao.getTransactions(category)
    }

    fun getAllTransactions(): Flow<MutableList<Transaction>>{
        return expenseDao.getAllTransactions()
    }

    suspend fun setBudget(monthBudget: MonthBudget){
        budgetDao.setMonthBudget(monthBudget)
    }

    suspend fun removeTransaction(transaction: Transaction){
        expenseDao.deleteTransaction(transaction)
    }

    suspend fun updateTransaction(transaction: Transaction){
        expenseDao.updateTransaction(transaction)
    }
}