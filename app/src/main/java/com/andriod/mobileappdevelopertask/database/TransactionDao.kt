package com.andriod.mobileappdevelopertask.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.andriod.mobileappdevelopertask.entity.Transaction

@Dao
interface TransactionDao {

    @Insert
    suspend fun insert(transaction: Transaction)
    @Delete
    suspend fun delete(transaction: Transaction)
    @Query("SELECT * FROM transactions ORDER BY date DESC")
    fun getAllTransactions(): LiveData<List<Transaction>>
}