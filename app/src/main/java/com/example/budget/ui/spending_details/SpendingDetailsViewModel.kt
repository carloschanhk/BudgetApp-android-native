package com.example.budget.ui.spending_details

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.example.budget.data.expense.Transaction
import com.example.budget.repository.BudgetRepository
import com.github.mikephil.charting.data.BarEntry
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

    fun getYAxisData(timeframe: String): MutableList<BarEntry> {
        val dates = getTimeframeDays(timeframe)
        val dataset = mutableListOf<BarEntry>()
        var entryDay = 0F
        for (date in dates) {
            var dailyExpenses = 0F
            val transactions = allExpenses
            if (transactions.isNotEmpty()) {
                for (item in transactions) {
                    if (item.date.toInstant().atZone(ZoneId.systemDefault())
                            .toLocalDate().dayOfYear == date.dayOfYear
                    ) {
                        dailyExpenses += item.cost
                    }
                }
            }
            dataset.add(BarEntry(entryDay, dailyExpenses))
            entryDay++
        }

        return dataset
    }

    fun setTimeFrame(timeFrame: String) {
        _selectedTimeFrame.value = timeFrame
    }
}