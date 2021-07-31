package com.example.budget.ui.dialog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budget.data.expense.Transaction
import com.example.budget.repository.BudgetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionCreationDialogViewModel @Inject constructor(private val budgetRepository: BudgetRepository) :
    ViewModel() {
    fun updateTransaction(transaction: Transaction) {
        viewModelScope.launch(Dispatchers.IO) {
            budgetRepository.updateTransaction(transaction)
        }
    }
    fun createTransaction(transaction: Transaction) {
        viewModelScope.launch(Dispatchers.IO) {
            budgetRepository.createTransaction(transaction)
        }
    }
}