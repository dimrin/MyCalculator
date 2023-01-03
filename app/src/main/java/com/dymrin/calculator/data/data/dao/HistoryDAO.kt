package com.dymrin.calculator.data.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dymrin.calculator.data.model.HistoryEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface HistoryDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(historyEntity: HistoryEntity)


    @Query("SELECT * FROM `history-table`")
    fun getResults(): Flow<List<HistoryEntity>>

    @Query("DELETE FROM `history-table`")
    suspend fun deleteAllResults()



}