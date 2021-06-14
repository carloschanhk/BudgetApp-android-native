package com.example.budget.ui.home

import android.util.Log
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

    val apparelsExpenses: LiveData<MutableList<Transaction>> = budgetRepository.getTransactions("Apparels").asLiveData()

    val foodExpenses: LiveData<MutableList<Transaction>> = budgetRepository.getTransactions("Food").asLiveData()

    val housingExpenses: LiveData<MutableList<Transaction>> = budgetRepository.getTransactions("Housing").asLiveData()

    val transitExpenses: LiveData<MutableList<Transaction>> = budgetRepository.getTransactions("Transit").asLiveData()

    val shoppingExpenses: LiveData<MutableList<Transaction>> = budgetRepository.getTransactions("Shopping").asLiveData()

    val healthExpenses: LiveData<MutableList<Transaction>> = budgetRepository.getTransactions("Health").asLiveData()

    val leisureExpenses: LiveData<MutableList<Transaction>> = budgetRepository.getTransactions("Leisure").asLiveData()

    private val _allExpenses =
        MutableLiveData<List<Triple<LiveData<MutableList<Transaction>>?, LiveData<Int>?, CategoryType>>>(listOf())
    val allExpenses: LiveData<List<Triple<LiveData<MutableList<Transaction>>?, LiveData<Int>?, CategoryType>>> get() = _allExpenses

    private val expenses = MediatorLiveData<List<Triple<List<Transaction>?, Int?, CategoryType>>>()


    val monthBudget: LiveData<Int> = budgetRepository.getMonthBudget("June").asLiveData()

    private val _allTransactions = budgetRepository.getAllTransactions().asLiveData()
    val totalExpenses: LiveData<Int> get() = Transformations.map(_allTransactions){
        var sum = 0F
        for (item in it){
            sum += item.cost!!
        }
        sum.toInt()
    }

    init {
        getAllExpenses()
    }

    private fun getAllExpenses() {
        viewModelScope.launch(Dispatchers.Main) {
            _allExpenses.value = listOf<Triple<LiveData<MutableList<Transaction>>?, LiveData<Int>?, CategoryType>>(
                Triple(apparelsExpenses, monthBudget, CategoryType.APPARELS),
                Triple(foodExpenses, monthBudget, CategoryType.FOOD),
                Triple(housingExpenses, monthBudget, CategoryType.HOUSING),
                Triple(transitExpenses, monthBudget, CategoryType.TRANSIT),
                Triple(shoppingExpenses, monthBudget, CategoryType.SHOPPING),
                Triple(healthExpenses, monthBudget, CategoryType.HEALTH),
                Triple(leisureExpenses, monthBudget, CategoryType.LEISURE)
            )
        }
    }
}