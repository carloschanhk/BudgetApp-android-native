package com.example.budget.ui.spending_details

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.example.budget.data.chart.BarChartEntry
import com.example.budget.data.expense.Transaction
import com.example.budget.repository.BudgetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.ZoneId
import java.util.*
import javax.inject.Inject

@HiltViewModel
@SuppressLint("SimpleDateFormat")
@RequiresApi(Build.VERSION_CODES.O)
class SpendingDetailsViewModel @Inject constructor(val budgetRepository: BudgetRepository) :
    ViewModel() {
    private val _selectedTimeFrame = MutableLiveData<String>("Last 7 Days")
    val selectedTimeFrame: LiveData<String> get() = _selectedTimeFrame

    fun getTransactions(category: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = budgetRepository.getTransactions(category)
            _filteredTransaction.postValue(response)
            _isDataSetChanged.postValue(true)
        }
    }

    private val _isDataSetChanged = MutableLiveData<Boolean>(false)
    val isDataSetChanged: LiveData<Boolean> get() = _isDataSetChanged

    private val _filteredTransaction = MutableLiveData<List<Transaction>>()
    val filteredTransaction: LiveData<List<Transaction>>
        get() = Transformations.switchMap(_filteredTransaction) { transactions ->
            Transformations.map(selectedTimeFrame) { timeframe ->
                val dates = getTimeframeDays(timeframe).map { it.dayOfYear }
                transactions.filter {
                    dates.indexOf(
                        it.date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().dayOfYear
                    ) != -1
                }

            }
        }

    private val _barChartData = MutableLiveData<MutableList<BarChartEntry>>(mutableListOf())
    val barChartData: LiveData<List<BarChartEntry>>
        get() = Transformations.switchMap(_barChartData) { dataset ->
            Transformations.switchMap(filteredTransaction) { transactions ->
                Transformations.map(selectedTimeFrame) { selectedTimeFrame ->
                    dataset.clear()
                    val dates = getTimeframeDays(selectedTimeFrame)
                    val expensesByDay = mutableListOf<Int>()
                    var highestDailyExpense = 0F
                    if (!transactions.isNullOrEmpty()) {
                        for (date in dates) {
                            var dailyExpense = 0F
                            for (item in transactions) {
                                if (item.date.toInstant().atZone(ZoneId.systemDefault())
                                        .toLocalDate().dayOfYear == date.dayOfYear
                                ) {
                                    dailyExpense += item.cost
                                }
                            }
                            highestDailyExpense =
                                if (dailyExpense > highestDailyExpense) dailyExpense else highestDailyExpense
                            expensesByDay.add(dailyExpense.toInt())
                        }
                        for (i in dates.indices) {
                            val percentageToHighestExpense =
                                expensesByDay[i] / highestDailyExpense * 100
                            dataset.add(
                                BarChartEntry(
                                    expensesByDay[i],
                                    percentageToHighestExpense.toInt(),
                                    dates[i]
                                )
                            )
                        }
                    }
                    _yAxisLabel.postValue(highestDailyExpense.toInt())
                    dataset
                }
            }
        }

    private val _yAxisLabel = MutableLiveData<Int>()
    val yAxisLabel: LiveData<Int> get() = _yAxisLabel


    val categoryExpenses: LiveData<Int>
        get() =
            Transformations.map(filteredTransaction)
            { transactions ->
                var sum = 0F
                transactions.forEach { sum += it.cost }
                sum.toInt()
            }

    fun getTimeframeDays(timeframe: String): List<LocalDate> {
        val calendar = Calendar.getInstance()
        calendar.time = Date()
        val dates: MutableList<LocalDate> =
            mutableListOf(calendar.time.toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
        var numOfDays = 0
        when (timeframe) {
            "Last 7 Days" -> numOfDays = 6
            "Last 30 Days" -> numOfDays = 29
        }
        repeat(numOfDays) {
            calendar.add(Calendar.DAY_OF_YEAR, -1)
            dates.add(calendar.time.toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
        }
        return dates.toList().sorted()
    }

    fun setTimeFrame(timeFrame: String) {
        _selectedTimeFrame.value = timeFrame
    }
}