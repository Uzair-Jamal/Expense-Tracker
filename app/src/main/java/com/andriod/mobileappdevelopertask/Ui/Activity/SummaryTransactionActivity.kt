package com.andriod.mobileappdevelopertask.Ui.Activity

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.andriod.mobileappdevelopertask.R
import com.andriod.mobileappdevelopertask.databinding.ActivitySummaryTransactionBinding
import com.andriod.mobileappdevelopertask.viewModel.TransactionViewmodel
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

class SummaryTransactionActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySummaryTransactionBinding
    private lateinit var viewModel: TransactionViewmodel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySummaryTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[TransactionViewmodel::class.java]

        viewModel.allTransactions.observe(this) { transactions ->
            val totalIncome = transactions
                .filter { it.type == "Income" }
                .sumOf { it.amount.toInt() }

            val totalExpense = transactions
                .filter { it.type == "Expense" }
                .sumOf { it.amount.toInt() }

            binding.incomeTv.text = "Rs $totalIncome"
            binding.expenseTv.text = "Rs $totalExpense"
            binding.netSavingsTv.text = "Rs ${totalIncome - totalExpense}"

            // Setup PieChart using these values
            val pieEntries = listOf(
                PieEntry(totalIncome.toFloat(), "Income"),
                PieEntry(totalExpense.toFloat(), "Expense")
            )

            val pieDataSet = PieDataSet(pieEntries, "Overview")
            pieDataSet.colors = listOf(
                R.color.income_color,
                R.color.expense_color
            )

            pieDataSet.valueTextColor = Color.WHITE
            pieDataSet.valueTextSize = 14f
            pieDataSet.sliceSpace = 2f

            val pieData = PieData(pieDataSet)

            binding.pieChart.apply {
                data = pieData
                centerText = "Expense Tracker"
                setCenterTextSize(16f)
                description.isEnabled = false
                isRotationEnabled = true
                animateY(1000)
                invalidate() // Refresh chart
            }
        }


    }
}