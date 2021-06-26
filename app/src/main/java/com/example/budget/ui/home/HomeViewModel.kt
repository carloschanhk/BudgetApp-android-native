package com.example.budget.ui.home

import android.annotation.SuppressLint
import android.view.View
import androidx.lifecycle.*
import androidx.navigation.NavController
import com.example.budget.R
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

    private val _apparelsExpenses = budgetRepository.getTransactions("Apparels").asLiveData()
    val apparelsExpenses: LiveData<List<Transaction>>
        get() = Transformations.switchMap(selectedMonth) { selectedMonth ->
            Transformations.map(_apparelsExpenses) {
                it.filter { transaction ->
                    SimpleDateFormat("MMMM").format(transaction.date!!) == selectedMonth
                }
            }
        }

    private val _foodExpenses = budgetRepository.getTransactions("Food").asLiveData()
    val foodExpenses: LiveData<List<Transaction>>
        get() = Transformations.switchMap(selectedMonth) { selectedMonth ->
            Transformations.map(_foodExpenses) {
                it.filter { transaction ->
                    SimpleDateFormat("MMMM").format(transaction.date!!) == selectedMonth
                }
            }
        }

    private val _housingExpenses = budgetRepository.getTransactions("Housing").asLiveData()
    val housingExpenses: LiveData<List<Transaction>>
        get() = Transformations.switchMap(selectedMonth) { selectedMonth ->
            Transformations.map(_housingExpenses) {
                it.filter { transaction ->
                    SimpleDateFormat("MMMM").format(transaction.date!!) == selectedMonth
                }
            }
        }

    private val _transitExpenses = budgetRepository.getTransactions("Transit").asLiveData()
    val transitExpenses: LiveData<List<Transaction>>
        get() = Transformations.switchMap(selectedMonth) { selectedMonth ->
            Transformations.map(_transitExpenses) {
                it.filter { transaction ->
                    SimpleDateFormat("MMMM").format(transaction.date!!) == selectedMonth
                }
            }
        }

    private val _shoppingExpenses = budgetRepository.getTransactions("Shopping").asLiveData()
    val shoppingExpenses: LiveData<List<Transaction>>
        get() = Transformations.switchMap(selectedMonth) { selectedMonth ->
            Transformations.map(_shoppingExpenses) {
                it.filter { transaction ->
                    SimpleDateFormat("MMMM").format(transaction.date!!) == selectedMonth
                }
            }
        }


    private val _healthExpenses = budgetRepository.getTransactions("Health").asLiveData()
    val healthExpenses: LiveData<List<Transaction>>
        get() = Transformations.switchMap(selectedMonth) { selectedMonth ->
            Transformations.map(_healthExpenses) {
                it.filter { transaction ->
                    SimpleDateFormat("MMMM").format(transaction.date!!) == selectedMonth
                }
            }
        }

    private val _leisureExpenses = budgetRepository.getTransactions("Leisure").asLiveData()
    val leisureExpenses: LiveData<List<Transaction>>
        get() = Transformations.switchMap(selectedMonth) { selectedMonth ->
            Transformations.map(_leisureExpenses) {
                it.filter { transaction ->
                    SimpleDateFormat("MMMM").format(transaction.date!!) == selectedMonth
                }
            }
        }

    val monthBudget = MediatorLiveData<LiveData<Int>>()

    private val _allTransactions = budgetRepository.getAllTransactions().asLiveData()
    val totalExpenses: LiveData<Int>
        get() = Transformations.switchMap(selectedMonth) { selectedMonth ->
            Transformations.map(_allTransactions) {
                val transactionsOfMonth = it.filter { transaction ->
                    SimpleDateFormat("MMMM").format(transaction.date!!) == selectedMonth
                }
                var sum = 0F
                for (transaction in transactionsOfMonth) {
                    sum += transaction.cost!!
                }
                sum.toInt()
            }
        }

    /**
     * For transaction items
     * **/

    private val _showTransactions = MutableLiveData<Boolean>(false)
    val showTransactions: LiveData<Boolean> get() = _showTransactions

    private val _sortByCost = MutableLiveData<Boolean>(true)
    val sortByCost: LiveData<Boolean> get() = _sortByCost

    val monthTransactions: LiveData<List<Transaction>>
        get() = Transformations.switchMap(selectedMonth) { selectedMonth ->
            Transformations.switchMap(sortByCost) { isSortByCost ->
                Transformations.map(_allTransactions) { allTransactions ->
                    val monthTransactions = allTransactions.filter { transaction ->
                        SimpleDateFormat("MMMM").format(transaction.date!!) == selectedMonth
                    }
                    if (isSortByCost) monthTransactions.sortedByDescending { it.cost } else monthTransactions.sortedByDescending { it.date }
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

    fun createTransaction(transaction: Transaction){
        viewModelScope.launch(Dispatchers.IO) {
            budgetRepository.createTransaction(transaction)
        }
    }

    fun removeTransaction(transaction: Transaction) {
        viewModelScope.launch(Dispatchers.IO) {
            budgetRepository.removeTransaction(transaction)
        }
    }

    /**
     * For editing transaction
     * **/
    var targetedTransaction: Transaction? = null
    fun editTransaction(transaction: Transaction, navController: NavController){
        targetedTransaction = transaction
        navController.navigate(R.id.action_FirstFragment_to_transactionCreationDialogFragment)
    }
    fun updateTransaction(transaction: Transaction){
        viewModelScope.launch(Dispatchers.IO) {
            budgetRepository.updateTransaction(transaction)
        }
    }
}