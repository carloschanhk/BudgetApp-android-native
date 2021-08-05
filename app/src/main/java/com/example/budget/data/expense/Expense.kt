package com.example.budget.data.expense

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

@Entity(tableName = "transaction")
data class Transaction(
    @PrimaryKey(autoGenerate = true)
    val transactionId: Int = 0,
    var category: String,
    var date: Date,
    var cost: Float,
    var title: String,
) : Serializable

