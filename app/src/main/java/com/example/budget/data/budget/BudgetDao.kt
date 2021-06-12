package com.example.budget.data.budget

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BudgetDao {
    @Query("SELECT budget FROM monthBudget WHERE month= :month")
    fun getMonthBudget(month: String): Flow<Int>

    @Query("SELECT * FROM monthBudget")
    fun getAllMonthBudget(): LiveData<List<MonthBudget>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun setMonthBudget(monthBudget: MonthBudget)
}