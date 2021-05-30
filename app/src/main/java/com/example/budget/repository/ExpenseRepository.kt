package com.example.budget.repository

import com.example.budget.data.expense.Category
import com.example.budget.data.expense.CategoryWithTransactions
import com.example.budget.data.expense.ExpenseDao
import com.example.budget.data.expense.Transaction
import javax.inject.Inject

class ExpenseRepository @Inject constructor(private val expenseDao: ExpenseDao) {
    suspend fun createCategory(category: Category){
        if (expenseDao.isCategoryExists(category.categoryTitle)){
            expenseDao.createCategory(category)
        }
    }

    suspend fun createTransaction(transaction: Transaction){
        expenseDao.createTransaction(transaction)
    }

    suspend fun getCategoryList(type:String): List<CategoryWithTransactions>{
        return expenseDao.getCategoryWithTransactions(type)
    }
}