package com.dymrin.calculator.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "history-table")
data class HistoryEntity(
    @PrimaryKey
    val result: String
)
