package com.example.budget.module

import android.content.Context
import androidx.room.Room
import com.example.budget.data.AppDatabase
import com.example.budget.data.expense.ExpenseDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Provider
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    fun provideExpenseDao(appDatabase: AppDatabase): ExpenseDao {
        return appDatabase.ExpenseDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context, expenseDaoProvider: Provider<ExpenseDao>): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "Budget"
        ).build()
    }
}