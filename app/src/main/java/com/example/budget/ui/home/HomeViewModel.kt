package com.example.budget.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budget.common.CategoryType
import com.example.budget.data.expense.Transaction
import com.example.budget.repository.BudgetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val budgetRepository: BudgetRepository) :
    ViewModel() {
    private val _selectedMonth = MutableLiveData<String>("June")
    val selectedMonth: LiveData<String> get() = _selectedMonth

    private val _apparelsExpenses = MutableLiveData<List<Transaction>>(listOf())
    val apparelsExpenses: LiveData<List<Transaction>> get() = _apparelsExpenses

    private val _foodExpenses = MutableLiveData<List<Transaction>>(listOf())
    val foodExpenses: LiveData<List<Transaction>> get() = _foodExpenses

    private val _housingExpenses = MutableLiveData<List<Transaction>>(listOf())
    val housingExpenses: LiveData<List<Transaction>> get() = _housingExpenses

    private val _transitExpenses = MutableLiveData<List<Transaction>>(listOf())
    val transitExpenses: LiveData<List<Transaction>> get() = _transitExpenses

    private val _shoppingExpenses = MutableLiveData<List<Transaction>>(listOf())
    val shoppingExpenses: LiveData<List<Transaction>> get() = _shoppingExpenses

    private val _healthExpenses = MutableLiveData<List<Transaction>>(listOf())
    val healthExpenses: LiveData<List<Transaction>> get() = _healthExpenses

    private val _leisureExpenses = MutableLiveData<List<Transaction>>(listOf())
    val leisureExpenses: LiveData<List<Transaction>> get() = _leisureExpenses

    private val _allExpenses =
        MutableLiveData<List<Triple<List<Transaction>, Int, CategoryType>>>(listOf())
    val allExpenses: MutableLiveData<List<Triple<List<Transaction>, Int, CategoryType>>> get() = _allExpenses

    private val _monthBudget = MutableLiveData<Int>(0)
    val monthBudget: LiveData<Int> get() = _monthBudget

    private val _totalExpenses = MutableLiveData<Int>(0)
    val totalExpenses: LiveData<Int> get() = _totalExpenses

    init {
        getAllExpenses()
    }

    private fun getAllExpenses() {
        viewModelScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                for (cat in CategoryType.values()) {
                    when (cat.type) {
                        "Apparels" -> _apparelsExpenses.postValue(
                            budgetRepository.getTransactions(cat.type)
                        )
                        "Food" -> _foodExpenses.postValue(
                            budgetRepository.getTransactions(cat.type)
                        )
                        "Housing" -> _housingExpenses.postValue(
                            budgetRepository.getTransactions(cat.type)
                        )
                        "Transit" -> _transitExpenses.postValue(
                            budgetRepository.getTransactions(cat.type)
                        )
                        "Shops" -> _shoppingExpenses.postValue(
                            budgetRepository.getTransactions(cat.type)
                        )
                        "Health" -> _healthExpenses.postValue(
                            budgetRepository.getTransactions(cat.type)
                        )
                        else -> _leisureExpenses.postValue(
                            budgetRepository.getTransactions(cat.type)
                        )
                    }
                }
            }
            _allExpenses.value = listOf<Triple<List<Transaction>, Int, CategoryType>>(
                Triple(apparelsExpenses.value!!, monthBudget.value!!, CategoryType.APPARELS),
                Triple(foodExpenses.value!!, monthBudget.value!!, CategoryType.FOOD),
                Triple(housingExpenses.value!!, monthBudget.value!!, CategoryType.HOUSING),
                Triple(transitExpenses.value!!, monthBudget.value!!, CategoryType.TRANSIT),
                Triple(shoppingExpenses.value!!, monthBudget.value!!, CategoryType.SHOPS),
                Triple(healthExpenses.value!!, monthBudget.value!!, CategoryType.HEALTH),
                Triple(leisureExpenses.value!!, monthBudget.value!!, CategoryType.LEISURE)
            )
        }
    }
}