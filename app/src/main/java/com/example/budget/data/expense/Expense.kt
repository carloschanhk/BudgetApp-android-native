package com.example.budget.data.expense

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "transaction")
data class Transaction(
    val category: String,
    val date: Date?,
    val cost: Float?,
    val title: String?){
    @PrimaryKey(autoGenerate = true)
    var transactionId: Int? = null
}

//@Entity(tableName = "category")
//data class Category(
//    @PrimaryKey val categoryTitle: String,
//    val type: CategoryType
//    )
//
//data class CategoryWithTransactions(
//    @Embedded val category: Category,
//    @Relation(
//        parentColumn = "categoryTitle",
//        entityColumn = "category"
//    )
//    val transaction: List<Transaction>
//)
