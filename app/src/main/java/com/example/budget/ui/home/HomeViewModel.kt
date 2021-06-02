package com.example.budget.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budget.common.CategoryType
import com.example.budget.data.expense.CategoryWithTransactions
import com.example.budget.data.expense.Transaction
import com.example.budget.repository.ExpenseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val expenseRepository: ExpenseRepository) :
    ViewModel() {
    private val _selectedMonth = MutableLiveData<String>("June")
    val selectedMonth: LiveData<String> get() = _selectedMonth

    private val _apparelsExpenses = MutableLiveData<List<CategoryWithTransactions>>(listOf())
    val apparelsExpenses: LiveData<List<CategoryWithTransactions>> get() = _apparelsExpenses

    private val _foodExpenses = MutableLiveData<List<CategoryWithTransactions>>(listOf())
    val foodExpenses: LiveData<List<CategoryWithTransactions>> get() = _foodExpenses

    private val _housingExpenses = MutableLiveData<List<CategoryWithTransactions>>(listOf())
    val housingExpenses: LiveData<List<CategoryWithTransactions>> get() = _housingExpenses

    private val _transitExpenses = MutableLiveData<List<CategoryWithTransactions>>(listOf())
    val transitExpenses: LiveData<List<CategoryWithTransactions>> get() = _transitExpenses

    private val _shoppingExpenses = MutableLiveData<List<CategoryWithTransactions>>(listOf())
    val shoppingExpenses: LiveData<List<CategoryWithTransactions>> get() = _shoppingExpenses

    private val _healthExpenses = MutableLiveData<List<CategoryWithTransactions>>(listOf())
    val healthExpenses: LiveData<List<CategoryWithTransactions>> get() = _healthExpenses

    private val _leisureExpenses = MutableLiveData<List<CategoryWithTransactions>>(listOf())
    val leisureExpenses: LiveData<List<CategoryWithTransactions>> get() = _leisureExpenses

    private val _allExpenses = MutableLiveData<List<List<CategoryWithTransactions>>>(listOf())
    val allExpenses: LiveData<List<List<CategoryWithTransactions>>> get() = _allExpenses

    private val _monthBudget = MutableLiveData<Int>(0)
    val monthBudget: LiveData<Int> get() = _monthBudget

    init {
        getAllExpenses()
    }

    fun totalExpenses(list: List<Transaction>): Int {
        var sum = 0
        for (item in list) {
            sum += item.cost!!
        }
        return sum
    }

    private fun getAllExpenses(){
        viewModelScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                for (cat in CategoryType.values()) {
                    when (cat.type) {
                        "Apparels" -> _apparelsExpenses.postValue(
                            expenseRepository.getCategoryList(cat.type)
                        )
                        "Food" -> _foodExpenses.postValue(
                            expenseRepository.getCategoryList(cat.type)
                        )
                        "Housing" -> _housingExpenses.postValue(
                            expenseRepository.getCategoryList(cat.type)
                        )
                        "Transit" -> _transitExpenses.postValue(
                            expenseRepository.getCategoryList(cat.type)
                        )
                        "Shops" -> _shoppingExpenses.postValue(
                            expenseRepository.getCategoryList(cat.type)
                        )
                        "Health" -> _healthExpenses.postValue(
                            expenseRepository.getCategoryList(cat.type)
                        )
                        else -> _leisureExpenses.postValue(
                            expenseRepository.getCategoryList(cat.type)
                        )
                    }
                }
            }
            _allExpenses.value = listOf(
                apparelsExpenses.value!!,
                foodExpenses.value!!,
                housingExpenses.value!!,
                transitExpenses.value!!,
                shoppingExpenses.value!!,
                healthExpenses.value!!,
                leisureExpenses.value!!
            )
        }
    }
}