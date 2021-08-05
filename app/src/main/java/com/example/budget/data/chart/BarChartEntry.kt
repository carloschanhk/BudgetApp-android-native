package com.example.budget.data.chart

import java.time.LocalDate

data class BarChartEntry(
        val dailyExpense: Int,
        val progress: Int,
        val date: LocalDate,
)