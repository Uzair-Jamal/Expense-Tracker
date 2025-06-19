package com.andriod.mobileappdevelopertask.repository

import com.andriod.mobileappdevelopertask.database.TransactionDao
import com.andriod.mobileappdevelopertask.entity.Transaction

class TransactionRepository(private val dao:TransactionDao) {

    val allTransactions = dao.getAllTransactions()

    suspend fun insertTransaction(transaction: Transaction) = dao.insert(transaction)
    suspend fun deleteTransaction(transaction: Transaction) = dao.delete(transaction)

}