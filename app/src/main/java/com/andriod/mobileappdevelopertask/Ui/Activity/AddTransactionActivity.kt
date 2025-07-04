package com.andriod.mobileappdevelopertask.Ui.Activity

import android.app.Application
import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.andriod.mobileappdevelopertask.R
import com.andriod.mobileappdevelopertask.databinding.ActivityAddTransactionBinding
import com.andriod.mobileappdevelopertask.databinding.CustomKeyboardViewBinding
import com.andriod.mobileappdevelopertask.entity.Transaction
import com.andriod.mobileappdevelopertask.viewModel.TransactionViewmodel
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
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
    private var amountText = ""
    private lateinit var viewModel: TransactionViewmodel
    private lateinit var keyboardBinding: CustomKeyboardViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        keyboardBinding = binding.customKeyboard

        viewModel = ViewModelProvider(this)[TransactionViewmodel::class.java]

        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select Date")
            .build()

        binding.amountInput.setOnClickListener {
            binding.customKeyboard.root.visibility = View.VISIBLE
        }

        binding.back.setOnClickListener {
            startActivity(Intent(this, TransactionActivity::class.java))
        }

        binding.categorySpinner.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                // Hide keyboard
                val imm = v.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(v.windowToken, 0)
                true
            } else {
                false
            }
        }


        binding.amountInput.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.customKeyboard.root.visibility = View.VISIBLE
            } else {
                binding.customKeyboard.root.visibility = View.GONE
            }
        }

        keyboardBinding.btnDone.setOnClickListener {
            // Hide custom keyboard
            keyboardBinding.root.visibility = View.GONE

            // Optionally clear focus
            binding.amountInput.clearFocus()
        }


        // Setup button click logic
        val buttons = listOf(
            keyboardBinding.btn0, keyboardBinding.btn1, keyboardBinding.btn2,
            keyboardBinding.btn3, keyboardBinding.btn4, keyboardBinding.btn5,
            keyboardBinding.btn6, keyboardBinding.btn7, keyboardBinding.btn8,
        )

        buttons.forEach { btn ->
            btn.setOnClickListener {
                amountText += btn.text
                binding.amountInput.setText(amountText)
            }
        }

        keyboardBinding.btnDel.setOnClickListener {
            if (amountText.isNotEmpty()) {
                amountText = amountText.dropLast(1)
                binding.amountInput.setText(amountText)
            }
        }


        binding.dateInput.setOnClickListener {
            datePicker.show(supportFragmentManager, "DATE_PICKER")
        }

        datePicker.addOnPositiveButtonClickListener {
//            date = it
            date = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(Date(it))
            binding.dateInput.text = date
        }

        binding.saveButton.setOnClickListener {
            amount = binding.amountInput.text.toString()
            description = binding.descriptionInput.text.toString()
            category = binding.categorySpinner.text.toString()

            val selectedId = binding.transactionTypeGroup.checkedRadioButtonId
            type = when (selectedId) {
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

            if (amount.isNotBlank() && type.isNotBlank()
                && category.isNotBlank() && date.isNotBlank()
            ) {
                viewModel.addTransaction(
                    transaction
                )
                MaterialAlertDialogBuilder(this)
                    .setMessage("Transaction inserted successfully")
                    .setCancelable(false)
                    .setPositiveButton("Ok") { dialog, _ ->
                        dialog.dismiss()
                        startActivity(Intent(this, TransactionActivity::class.java))
                    }
                    .show()
            } else {
                MaterialAlertDialogBuilder(this)
                    .setMessage("Please provide value for required fields")
                    .setCancelable(false)
                    .setPositiveButton("Ok") { dialog, _ -> dialog.dismiss() }
                    .show()
            }
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val view = currentFocus
        if (view != null && ev.action == MotionEvent.ACTION_DOWN) {
            val outRectEditText = Rect()
            val outRectKeyboard = Rect()

            binding.amountInput.getGlobalVisibleRect(outRectEditText)
            binding.customKeyboard.root.getGlobalVisibleRect(outRectKeyboard)

            val isOutsideEditText = !outRectEditText.contains(ev.rawX.toInt(), ev.rawY.toInt())
            val isOutsideKeyboard = !outRectKeyboard.contains(ev.rawX.toInt(), ev.rawY.toInt())

            if (isOutsideEditText && isOutsideKeyboard) {
                view.clearFocus()
                binding.customKeyboard.root.visibility = View.GONE
            }
        }
        return super.dispatchTouchEvent(ev)
    }


}