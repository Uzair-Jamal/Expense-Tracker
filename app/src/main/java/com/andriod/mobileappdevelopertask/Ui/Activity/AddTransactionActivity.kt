package com.andriod.mobileappdevelopertask.Ui.Activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.andriod.mobileappdevelopertask.R
import com.andriod.mobileappdevelopertask.databinding.ActivityAddTransactionBinding

class AddTransactionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddTransactionBinding
    private var category: String = ""
    private var type: String = ""
    private var amount: Double = 0.0
    private var description: String = ""
    private var date: Long = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.transactionTypeGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.incomeRadio -> {
                    type = binding.incomeRadio.text.toString()
                }

                R.id.expenseRadio -> {
                    type = binding.expenseRadio.text.toString()
                }
            }
        }

        amount = binding.amountInput.text.toString().toDouble()
        description = binding.descriptionInput.text.toString()
        category = binding.categorySpinner.selectedItem.toString()
    }
}