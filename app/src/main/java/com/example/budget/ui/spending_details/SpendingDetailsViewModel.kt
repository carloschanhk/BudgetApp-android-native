package com.example.budget.ui.spending_details

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
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

    private val _isLoading = MutableLiveData<Boolean>(true)
    val isLoading: LiveData<Boolean> get() = _isLoading
    lateinit var allExpenses: List<Transaction>

    fun getAllExpenses(category: String) {
        viewModelScope.launch(Dispatchers.IO) {
            allExpenses = budgetRepository.getTransactions(category)
            _filteredTransaction.postValue(allExpenses)
            _isLoading.postValue(false)
        }
    }

//    private val _xAxisValues = MutableLiveData<MutableList<String>>(mutableListOf())
//    val xAxisValues: LiveData<List<String>>
//        get() = Transformations.switchMap(selectedTimeFrame) { timeFrame ->
//            Transformations.map(_xAxisValues) { list ->
//                list.clear()
//                when (timeFrame) {
////                    "Week" -> list.addAll(listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"))
//                    "Week" -> list.addAll(getTimeframeDays().sortedBy { it }.map {
//                        it.format(
//                            DateTimeFormatter.ofPattern(
//                                "E"
//                            )
//                        )
//                    })
//                    "Year" -> list.addAll(
//                        listOf(
//                            "Jan",
//                            "Feb",
//                            "Mar",
//                            "Apr",
//                            "May",
//                            "Jun",
//                            "Jul",
//                            "Aug",
//                            "Sep",
//                            "Oct",
//                            "Nov",
//                            "Dec"
//                        )
//                    )
//                }
//                list
//            }
//        }

    private val _filteredTransaction = MutableLiveData<List<Transaction>>(listOf())
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

    private val _barChartData = MutableLiveData<List<BarChartEntry>>(listOf())
    val barChartData: LiveData<List<BarChartEntry>> get() = _barChartData

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
        Log.d("Testing", "getTimeframeDays: $dates")
        return dates.toList().sorted()
    }

//    fun getYAxisData(timeframe: String): MutableList<BarEntry> {
//        val dates = getTimeframeDays(timeframe)
//        val dataset = mutableListOf<BarEntry>()
//        var entryDay = 0F
//        for (date in dates) {
//            var dailyExpenses = 0F
//            val transactions = allExpenses
//            if (transactions.isNotEmpty()) {
//                for (item in transactions) {
//                    if (item.date.toInstant().atZone(ZoneId.systemDefault())
//                            .toLocalDate().dayOfYear == date.dayOfYear
//                    ) {
//                        dailyExpenses += item.cost
//                    }
//                }
//            }
//            dataset.add(BarEntry(entryDay, dailyExpenses))
//            entryDay++
//        }
//
//        return dataset
//    }

    fun loadBarChartData(timeframe: String) {
        val dataset = mutableListOf<BarChartEntry>()
        val expensesByDay = mutableListOf<Int>()
        val dates = getTimeframeDays(timeframe)
        var highestDailyExpenses = 0F
        val transactions = allExpenses
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
                highestDailyExpenses =
                    if (dailyExpense > highestDailyExpenses) dailyExpense else highestDailyExpenses
                expensesByDay.add(dailyExpense.toInt())
            }
            for (i in dates.indices) {
                val percentageToHighestExpense = expensesByDay[i] / highestDailyExpenses * 100
                dataset.add(
                    BarChartEntry(
                        expensesByDay[i],
                        percentageToHighestExpense.toInt(),
                        dates[i]
                    )
                )
            }
        }
        _barChartData.postValue(dataset)
        _yAxisLabel.postValue(highestDailyExpenses.toInt())
    }

    fun setTimeFrame(timeFrame: String) {
        _selectedTimeFrame.value = timeFrame
    }
}