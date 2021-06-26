package com.example.budget.data.expense

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "transaction")
data class Transaction(
    @PrimaryKey(autoGenerate = true)
    var transactionId: Int? = 0,
    var category: String,
    var date: Date?,
    var cost: Float?,
    var title: String?)

