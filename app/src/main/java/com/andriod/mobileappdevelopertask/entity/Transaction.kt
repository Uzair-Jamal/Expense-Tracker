package com.andriod.mobileappdevelopertask.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "transactions")
@Parcelize
data class Transaction(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id")
    val id: Int = 0,
    @ColumnInfo("amount")
    val amount: String,
    @ColumnInfo("type")
    val type: String,
    @ColumnInfo("category")
    val category: String,
    @ColumnInfo("date")
    val date: String,
    @ColumnInfo("description")
    val description: String? = null
):Parcelable
