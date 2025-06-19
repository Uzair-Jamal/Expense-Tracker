package com.andriod.mobileappdevelopertask.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "transactions")
@Parcelize
data class Transaction(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val amount: String,
    val type: String,
    val category: String,
    val date: String,
    val description: String? = null
):Parcelable
