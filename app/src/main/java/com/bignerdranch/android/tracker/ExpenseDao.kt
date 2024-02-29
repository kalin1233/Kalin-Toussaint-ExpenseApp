package com.bignerdranch.android.tracker

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {
    @Query("SELECT * FROM expenses")
    fun getAllExpenses(): Flow<List<Expense>>

    @Query("SELECT * FROM expenses WHERE date = :dateInMillis")
    fun getExpensesByDate(dateInMillis: String): Flow<List<Expense>>

    @Query("SELECT * FROM expenses WHERE category = :category")
    fun getExpensesByCategory(category: String): Flow<List<Expense>>

    @Insert
    fun insertExpense(expense: Expense)

    @Update
    suspend fun updateExpense(expense: Expense)

}