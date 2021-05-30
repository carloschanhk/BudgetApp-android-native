package com.example.budget.data.expense

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "transaction")
data class Transaction(
    val category: String,
    val date: String?,
    val cost: Int?,
    val title: String?){
    @PrimaryKey(autoGenerate = true)
    var transactionId: Int? = null
}

@Entity(tableName = "category")
data class Category(
    @PrimaryKey val categoryTitle: String,
    )

data class CategoryWithTransactions(
    @Embedded val category: Category,
    @Relation(
        parentColumn = "categoryTitle",
        entityColumn = "category"
    )
    val transaction: List<Transaction>
)
