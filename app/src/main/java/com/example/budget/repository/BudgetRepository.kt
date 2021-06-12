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

    fun getTransactions(category: String): Flow<List<Transaction>> {
        return expenseDao.getTransactions(category)
    }

    suspend fun setBudget(monthBudget: MonthBudget){
        return budgetDao.setMonthBudget(monthBudget)
    }
}