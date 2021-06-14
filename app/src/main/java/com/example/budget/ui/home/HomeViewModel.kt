package com.example.budget.ui.home

import android.view.View
import androidx.lifecycle.*
import com.example.budget.R
import com.example.budget.data.expense.Transaction
import com.example.budget.repository.BudgetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val budgetRepository: BudgetRepository) :
    ViewModel() {
    private val _selectedMonth = MutableLiveData<String>(SimpleDateFormat("MMMM").format(Date()))
    val selectedMonth: LiveData<String> get() = _selectedMonth

    val apparelsExpenses: LiveData<MutableList<Transaction>> =
        budgetRepository.getTransactions("Apparels").asLiveData()

    val foodExpenses: LiveData<MutableList<Transaction>> =
        budgetRepository.getTransactions("Food").asLiveData()

    val housingExpenses: LiveData<MutableList<Transaction>> =
        budgetRepository.getTransactions("Housing").asLiveData()

    val transitExpenses: LiveData<MutableList<Transaction>> =
        budgetRepository.getTransactions("Transit").asLiveData()

    val shoppingExpenses: LiveData<MutableList<Transaction>> =
        budgetRepository.getTransactions("Shopping").asLiveData()

    val healthExpenses: LiveData<MutableList<Transaction>> =
        budgetRepository.getTransactions("Health").asLiveData()

    val leisureExpenses: LiveData<MutableList<Transaction>> =
        budgetRepository.getTransactions("Leisure").asLiveData()

//    val monthBudget: LiveData<Int> =
//        budgetRepository.getMonthBudget(selectedMonth.value!!).asLiveData()

    val monthBudget = MediatorLiveData<LiveData<Int>>()

    private val _allTransactions = budgetRepository.getAllTransactions().asLiveData()
    val totalExpenses: LiveData<Int>
        get() = Transformations.map(_allTransactions) {
            var sum = 0F
            for (item in it) {
                sum += item.cost!!
            }
            sum.toInt()
        }

    init {
        monthBudget.addSource(selectedMonth) { month ->
            monthBudget.value = budgetRepository.getMonthBudget(month).asLiveData()
        }
    }

    fun onChangeMonth(button: View) {
        val months: List<String> =
            listOf<String>(
                "January",
                "February",
                "March",
                "April",
                "May",
                "June",
                "July",
                "August",
                "September",
                "October",
                "November",
                "December"
            )
        val currentMonth = selectedMonth.value
        val monthIndex = months.indexOf(currentMonth)
        if (button.id == R.id.btn_next_month) {
            if (monthIndex == 11) {
                _selectedMonth.value = months[0]
            } else {
                _selectedMonth.value = months[monthIndex + 1]
            }
        } else {
            if (monthIndex == 0){
                _selectedMonth.value = months[11]
            } else {
                _selectedMonth.value = months[monthIndex - 1]
            }
        }
    }
}