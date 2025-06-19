package com.andriod.mobileappdevelopertask.Ui.Activity

import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.andriod.mobileappdevelopertask.R
import com.andriod.mobileappdevelopertask.databinding.ActivityAddTransactionBinding
import com.andriod.mobileappdevelopertask.entity.Transaction
import com.andriod.mobileappdevelopertask.viewModel.TransactionViewmodel
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AddTransactionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddTransactionBinding
    private var category: String = ""
    private var type: String = ""
    private var amount: String = ""
    private var description: String = ""
    private var date: String = ""
    private lateinit var viewModel: TransactionViewmodel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[TransactionViewmodel::class.java]

        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select Date")
            .build()

        binding.dateInput.setOnClickListener {
            datePicker.show(supportFragmentManager, "DATE_PICKER")
        }

        datePicker.addOnPositiveButtonClickListener {
            val dateLong = it
            date = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(Date(it))
            binding.dateInput.text = date
        }

        binding.saveButton.setOnClickListener {
            amount = binding.amountInput.text.toString()
            description = binding.descriptionInput.text.toString()
            category = binding.categorySpinner.selectedItem.toString()

            val selectedId = binding.transactionTypeGroup.checkedRadioButtonId
            type = when(selectedId){
                R.id.expenseRadio -> binding.expenseRadio.text.toString()
                R.id.incomeRadio -> binding.incomeRadio.text.toString()
                else -> ""
            }

            val transaction = Transaction(
                amount = amount,
                type = type,
                category = category,
                date = date,
                description = description
            )
            Log.d("transaction", "$transaction")

            viewModel.addTransaction(
                transaction
            )

        }
    }
}