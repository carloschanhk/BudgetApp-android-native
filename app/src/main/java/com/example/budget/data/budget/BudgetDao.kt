package com.example.budget.data.budget

import androidx.room.Dao
import androidx.room.Query

@Dao
interface BudgetDao {
    @Query("SELECT * FROM monthBudget WHERE month= :month")
    fun getMonthBudget(month: String): MonthBudget
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun setMonthBudget(monthBudget: MonthBudget)
}