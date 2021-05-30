package com.example.budget.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.budget.data.expense.CategoryWithTransactions
import com.example.budget.repository.ExpenseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(expenseRepository: ExpenseRepository) : ViewModel() {
    val selectedMonth = MutableLiveData<String>("June")

    val _apparelsExpenses = MutableLiveData<List<CategoryWithTransactions>>()
    val apparelsExpenses : LiveData<List<CategoryWithTransactions>> get() = _apparelsExpenses

    private val _foodExpenses = MutableLiveData<List<CategoryWithTransactions>>()
    val foodExpenses : LiveData<List<CategoryWithTransactions>> get() = _foodExpenses

    private val _housingExpenses = MutableLiveData<List<CategoryWithTransactions>>()
    val housingExpenses : LiveData<List<CategoryWithTransactions>> get() = _housingExpenses

    private val _transitExpenses = MutableLiveData<List<CategoryWithTransactions>>()
    val transitExpenses : LiveData<List<CategoryWithTransactions>> get() = _transitExpenses

    private val _shoppingExpenses = MutableLiveData<List<CategoryWithTransactions>>()
    val shoppingExpenses : LiveData<List<CategoryWithTransactions>> get() = _shoppingExpenses

    private val _healthExpenses = MutableLiveData<List<CategoryWithTransactions>>()
    val healthExpenses : LiveData<List<CategoryWithTransactions>> get() = _healthExpenses

//    init {
//        viewModelScope.launch {
//            CategoryType.values().forEach {
//                when (it) {
//                    CategoryType.APPARELS -> _apparelsExpenses.value =
//                        expenseRepository.getCategoryList(it.type)
//                    CategoryType.FOOD -> _foodExpenses.value =
//                        expenseRepository.getCategoryList(it.type)
//                    CategoryType.HOUSING -> _housingExpenses.value =
//                        expenseRepository.getCategoryList(it.type)
//                    CategoryType.TRANSIT -> _transitExpenses.value =
//                        expenseRepository.getCategoryList(it.type)
//                    CategoryType.SHOPS -> _shoppingExpenses.value =
//                        expenseRepository.getCategoryList(it.type)
//                    CategoryType.HEALTH -> _healthExpenses.value =
//                        expenseRepository.getCategoryList(it.type)
//                }
//            }
//        }
//    }


}