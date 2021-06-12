package com.example.budget.ui.home

import androidx.lifecycle.*
import com.example.budget.common.CategoryType
import com.example.budget.data.expense.Transaction
import com.example.budget.repository.BudgetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val budgetRepository: BudgetRepository) :
    ViewModel() {
    private val _selectedMonth = MutableLiveData<String>("June")
    val selectedMonth: LiveData<String> get() = _selectedMonth

    val apparelsExpenses: LiveData<List<Transaction>> = budgetRepository.getTransactions("Apparels").asLiveData()

    val foodExpenses: LiveData<List<Transaction>> = budgetRepository.getTransactions("Food").asLiveData()

    val housingExpenses: LiveData<List<Transaction>> = budgetRepository.getTransactions("Housing").asLiveData()

    val transitExpenses: LiveData<List<Transaction>> = budgetRepository.getTransactions("Transit").asLiveData()

    val shoppingExpenses: LiveData<List<Transaction>> = budgetRepository.getTransactions("Shopping").asLiveData()

    val healthExpenses: LiveData<List<Transaction>> = budgetRepository.getTransactions("Health").asLiveData()

    val leisureExpenses: LiveData<List<Transaction>> = budgetRepository.getTransactions("Leisure").asLiveData()

    private val _allExpenses =
        MutableLiveData<List<Triple<List<Transaction>?, Int?, CategoryType>>>(listOf())
    val allExpenses: LiveData<List<Triple<List<Transaction>?, Int?, CategoryType>>> get() = _allExpenses

    val monthBudget: LiveData<Int> = budgetRepository.getMonthBudget("June").asLiveData()

    private val _totalExpenses = MutableLiveData<Int>(0)
    val totalExpenses: LiveData<Int> get() = _totalExpenses

    init {
        getAllExpenses()
    }

    private fun getAllExpenses() {
        viewModelScope.launch(Dispatchers.Main) {
            _allExpenses.value = listOf<Triple<List<Transaction>?, Int?, CategoryType>>(
                Triple(apparelsExpenses?.value, monthBudget?.value, CategoryType.APPARELS),
                Triple(foodExpenses?.value, monthBudget?.value, CategoryType.FOOD),
                Triple(housingExpenses?.value, monthBudget?.value, CategoryType.HOUSING),
                Triple(transitExpenses?.value, monthBudget?.value, CategoryType.TRANSIT),
                Triple(shoppingExpenses?.value, monthBudget?.value, CategoryType.SHOPPING),
                Triple(healthExpenses?.value, monthBudget?.value, CategoryType.HEALTH),
                Triple(leisureExpenses?.value, monthBudget?.value, CategoryType.LEISURE)
            )
        }
    }
}