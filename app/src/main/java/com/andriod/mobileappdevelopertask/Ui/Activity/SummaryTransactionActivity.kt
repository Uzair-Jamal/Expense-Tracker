package com.andriod.mobileappdevelopertask.Ui.Activity

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import java.time.format.DateTimeFormatter
import androidx.lifecycle.ViewModelProvider
import com.andriod.mobileappdevelopertask.MonthlySummary
import com.andriod.mobileappdevelopertask.R
import com.andriod.mobileappdevelopertask.databinding.ActivitySummaryTransactionBinding
import com.andriod.mobileappdevelopertask.viewModel.TransactionViewmodel
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import java.time.LocalDate
import java.time.YearMonth
import java.util.Locale

class SummaryTransactionActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySummaryTransactionBinding
    private lateinit var viewModel: TransactionViewmodel
    private var totalIncome = 0
    private var totalExpense = 0
    private var netSavings = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySummaryTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[TransactionViewmodel::class.java]

        viewModel.allTransactions.observe(this) { transactions ->
            totalIncome = transactions
                .filter { it.type == "Income" }
                .sumOf { it.amount.toInt() }

            totalExpense = transactions
                .filter { it.type == "Expense" }
                .sumOf { it.amount.toInt() }

            netSavings = totalIncome - totalExpense

            binding.incomeTv.text = "Rs $totalIncome"
            binding.expenseTv.text = "Rs $totalExpense"
            binding.netSavingsTv.text = "Rs $netSavings"

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
