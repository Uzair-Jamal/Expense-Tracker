package com.andriod.mobileappdevelopertask

import java.time.YearMonth

data class MonthlySummary(
    val month: YearMonth,
    val income: Int,
    val expense: Int,
    val savings: Int
)
