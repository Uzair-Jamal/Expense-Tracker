package com.andriod.mobileappdevelopertask.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.andriod.mobileappdevelopertask.database.TransactionDatabase
import com.andriod.mobileappdevelopertask.entity.Transaction
import com.andriod.mobileappdevelopertask.repository.TransactionRepository
import kotlinx.coroutines.launch

class TransactionViewmodel(application: Application) : AndroidViewModel(application) {

    private val dao = TransactionDatabase.getDatabase(application).transactionDao()
    private val repository = TransactionRepository(dao)

    val allTransactions = repository.allTransactions

    fun addTransaction(transaction: Transaction) = viewModelScope.launch {
        repository.insertTransaction(transaction)
    }

    fun deleteTransaction(transaction: Transaction) = viewModelScope.launch {
        repository.deleteTransaction(transaction)
    }

}