package com.example.budget.ui.home

import android.annotation.SuppressLint
import android.view.View
import androidx.lifecycle.*
import com.example.budget.R
import com.example.budget.common.CategoryType
import com.example.budget.data.expense.Transaction
import com.example.budget.repository.BudgetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
@SuppressLint("SimpleDateFormat")
class HomeViewModel @Inject constructor(private val budgetRepository: BudgetRepository) :
    ViewModel() {

    private val _selectedMonth = MutableLiveData<String>(SimpleDateFormat("MMMM").format(Date()))
    val selectedMonth: LiveData<String> get() = _selectedMonth

    val monthBudget = MediatorLiveData<LiveData<Int>>()

    private val _allTransactions = budgetRepository.getAllTransactions().asLiveData()
    val totalExpenses: LiveData<Int>
        get() = Transformations.switchMap(selectedMonth) { selectedMonth ->
            Transformations.map(_allTransactions) {
                val transactionsOfMonth = it.filter { transaction ->
                    SimpleDateFormat("MMMM").format(transaction.date) == selectedMonth
                }
                var sum = 0F
                for (transaction in transactionsOfMonth) {
                    sum += transaction.cost
                }
                sum.toInt()
            }
        }

    //Transaction item

    private val _showTransactions = MutableLiveData<Boolean>(false)
    val showTransactions: LiveData<Boolean> get() = _showTransactions

    private val _sortByCost = MutableLiveData<Boolean>(true)
    val sortByCost: LiveData<Boolean> get() = _sortByCost

    val monthTransactions: LiveData<List<Transaction>>
        get() = Transformations.switchMap(selectedMonth) { selectedMonth ->
            Transformations.switchMap(sortByCost) { isSortByCost ->
                Transformations.map(_allTransactions) { allTransactions ->
                    val monthTransactions = allTransactions.filter { transaction ->
                        SimpleDateFormat("MMMM").format(transaction.date) == selectedMonth
                    }
                    if (isSortByCost) monthTransactions.sortedByDescending { it.cost } else monthTransactions.sortedByDescending { it.date }
                }
            }
        }

    private val _allCategory =
        MutableLiveData<MutableList<Pair<CategoryType, List<Transaction>?>>>(mutableListOf<Pair<CategoryType, List<Transaction>?>>())

    val allCategory: LiveData<List<Pair<CategoryType, List<Transaction>?>>>
        get() = Transformations.switchMap(monthTransactions) { transactions ->
            Transformations.map(_allCategory) {
                val apparels = mutableListOf<Transaction>()
                val food = mutableListOf<Transaction>()
                val health = mutableListOf<Transaction>()
                val shopping = mutableListOf<Transaction>()
                val leisure = mutableListOf<Transaction>()
                val transit = mutableListOf<Transaction>()
                val housing = mutableListOf<Transaction>()
                for (item in transactions) {
                    when (item.category) {
                        "Apparels" -> apparels.add(item)
                        "Food" -> food.add(item)
                        "Health" -> health.add(item)
                        "Shopping" -> shopping.add(item)
                        "Leisure" -> leisure.add(item)
                        "Transit" -> transit.add(item)
                        "Housing" -> housing.add(item)
                    }
                }
                it.clear()
                it.addAll(
                    listOf<Pair<CategoryType, List<Transaction>?>>(
                        Pair(CategoryType.APPARELS, apparels),
                        Pair(CategoryType.FOOD, food),
                        Pair(CategoryType.HOUSING, housing),
                        Pair(CategoryType.SHOPPING, shopping),
                        Pair(CategoryType.HEALTH, health),
                        Pair(CategoryType.TRANSIT, transit),
                        Pair(CategoryType.LEISURE, leisure)
                    )
                )
                it.sortedByDescending { pair ->
                    var sum = 0F
                    pair.second?.let {
                        for (item in it) {
                            sum += item.cost
                        }
                    }
                    sum
                }
            }
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
            if (monthIndex == 0) {
                _selectedMonth.value = months[11]
            } else {
                _selectedMonth.value = months[monthIndex - 1]
            }
        }
    }

    fun onChangeDisplay(button: View) {
        _showTransactions.value = button.id == R.id.btn_transactions
    }

    fun onChangeSortBy(id: Int) {
        _sortByCost.value = id == R.id.action_sort_by_cost
    }

    fun createTransaction(transaction: Transaction) {
        viewModelScope.launch(Dispatchers.IO) {
            budgetRepository.createTransaction(transaction)
        }
    }

    fun removeTransaction(transaction: Transaction) {
        viewModelScope.launch(Dispatchers.IO) {
            budgetRepository.removeTransaction(transaction)
        }
    }

    //Editing Transaction

    var targetedTransaction: Transaction? = null
    fun editTransaction(transaction: Transaction) {
        targetedTransaction = transaction
    }

    fun updateTransaction(transaction: Transaction) {
        viewModelScope.launch(Dispatchers.IO) {
            budgetRepository.updateTransaction(transaction)
        }
    }
}