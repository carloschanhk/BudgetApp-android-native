package com.example.budget.module

import android.content.Context
import androidx.room.Room
import com.example.budget.data.AppDatabase
import com.example.budget.data.budget.BudgetDao
import com.example.budget.data.expense.ExpenseDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    fun provideExpenseDao(appDatabase: AppDatabase): ExpenseDao {
        return appDatabase.ExpenseDao()
    }

    @Provides
    fun provideBudgetDao(appDatabase: AppDatabase): BudgetDao {
        return appDatabase.BudgetDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "Budget"
        ).build()
    }
}