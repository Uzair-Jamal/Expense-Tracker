package com.andriod.mobileappdevelopertask.Ui.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.andriod.mobileappdevelopertask.R
import com.andriod.mobileappdevelopertask.adapter.TransactionAdapter
import com.andriod.mobileappdevelopertask.databinding.ActivityTransactionBinding
import com.andriod.mobileappdevelopertask.entity.Transaction
import com.andriod.mobileappdevelopertask.viewModel.TransactionViewmodel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.datepicker.MaterialDatePicker
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

class TransactionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTransactionBinding
    private lateinit var transactionAdapter: TransactionAdapter
    private lateinit var viewModel: TransactionViewmodel
    private var allTransactions: List<Transaction> = emptyList()


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

        binding.btnFilter.setOnClickListener {
            showDateFilterBottomSheet()
        }
    }

    private fun setupRecyclerView() {
        transactionAdapter = TransactionAdapter()

        binding.transactionRv.apply {
            layoutManager =
                LinearLayoutManager(this@TransactionActivity, LinearLayoutManager.VERTICAL, false)
            adapter = transactionAdapter
        }

        viewModel.allTransactions.observe(this) { transactions ->
            allTransactions = transactions
            Log.d(
                "transaction",
                "All Transactions: ${transactions.size}, Transaction: $transactions"
            )
            transactionAdapter.differ.submitList(transactions)
        }
    }

    private fun filterTransactionsByDate(startDate: LocalDate, endDate: LocalDate) {
        val formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy", Locale.ENGLISH)

        val filteredList = allTransactions.filter {
            val transactionDate = LocalDate.parse(it.date, formatter)
            transactionDate in startDate..endDate
        }

        transactionAdapter.differ.submitList(filteredList)
    }

    private fun showDateFilterBottomSheet() {
        val dialogView = layoutInflater.inflate(R.layout.date_filter_bottom_sheet, null)
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(dialogView)

        val startDateEt = dialogView.findViewById<EditText>(R.id.startDateEt)
        val endDateEt = dialogView.findViewById<EditText>(R.id.endDateEt)
        val submitBtn = dialogView.findViewById<Button>(R.id.submitBtn)

        val formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy")

        var selectedStartDate: LocalDate? = null
        var selectedEndDate: LocalDate? = null

        startDateEt.setOnClickListener {
            showDatePicker { date ->
                selectedStartDate = date
                startDateEt.setText(date.format(formatter))

            }
        }

        endDateEt.setOnClickListener {
            showDatePicker { date ->
                selectedEndDate = date
                endDateEt.setText(date.format(formatter))

            }
        }

        submitBtn.setOnClickListener {
            if (selectedStartDate != null && selectedEndDate != null) {
                filterTransactionsByDate(selectedStartDate!!, selectedEndDate!!)
                dialog.dismiss()
            } else {
                Toast.makeText(this, "Please select both start and end dates", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        dialog.show()
    }

    private fun showDatePicker(onDateSelected: (LocalDate) -> Unit) {
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select Date")
            .build()

        datePicker.show(supportFragmentManager, "material_date_picker")

        datePicker.addOnPositiveButtonClickListener { millis ->
            val selectedDate = Instant.ofEpochMilli(millis)
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
            onDateSelected(selectedDate)
        }
    }

}