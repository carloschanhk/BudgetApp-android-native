package com.example.budget.repository

import com.example.budget.data.budget.BudgetDao
import com.example.budget.data.expense.ExpenseDao
import com.example.budget.data.expense.Transaction
import javax.inject.Inject

class BudgetRepository @Inject constructor(private val expenseDao: ExpenseDao, private val budgetDao: BudgetDao) {
//    suspend fun createCategory(category: Category){
//        if (expenseDao.isCategoryExists(category.categoryTitle)){
//            expenseDao.createCategory(category)
//        }
//    }

    suspend fun createTransaction(transaction: Transaction){
        expenseDao.createTransaction(transaction)
    }

    suspend fun getTransactions(category: String): List<Transaction> {
        return expenseDao.getTransactions(category)
    }

//    suspend fun getCategoryList(type:String): List<CategoryWithTransactions>{
//
//        val list = expenseDao.getCategoryWithTransactions(type)
//
//        Log.d("repository", "$list")
//        return list
//    }
}