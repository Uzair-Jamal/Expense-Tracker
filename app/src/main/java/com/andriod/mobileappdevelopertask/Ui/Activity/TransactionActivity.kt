package com.andriod.mobileappdevelopertask.Ui.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.andriod.mobileappdevelopertask.R
import com.andriod.mobileappdevelopertask.adapter.TransactionAdapter
import com.andriod.mobileappdevelopertask.databinding.ActivityTransactionBinding
import com.andriod.mobileappdevelopertask.viewModel.TransactionViewmodel

class TransactionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTransactionBinding
    private lateinit var transactionAdapter: TransactionAdapter
    private lateinit var viewModel: TransactionViewmodel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[TransactionViewmodel::class.java]

        setupRecyclerView()
        binding.addTransactionBtn.setOnClickListener {
            startActivity(Intent(this, AddTransactionActivity::class.java))
        }

        binding.summaryTransactionBtn.setOnClickListener {
            startActivity(Intent(this, SummaryTransactionActivity::class.java))
        }
    }

    private fun setupRecyclerView(){
        transactionAdapter = TransactionAdapter()

        binding.transactionRv.apply {
            layoutManager = LinearLayoutManager(this@TransactionActivity, LinearLayoutManager.VERTICAL, false)
            adapter = transactionAdapter
        }

        viewModel.allTransactions.observe(this) { transactions ->
            Log.d("transaction", "All Transactions: ${transactions.size}, Transaction: $transactions")
            transactionAdapter.differ.submitList(transactions)
        }
    }
}