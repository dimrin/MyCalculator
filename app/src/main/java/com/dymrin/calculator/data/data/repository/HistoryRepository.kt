package com.dymrin.calculator.data.data.repository

import com.dymrin.calculator.data.data.dao.HistoryDAO
import com.dymrin.calculator.data.model.HistoryEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn


class HistoryRepository(private val dao: HistoryDAO) {

    suspend fun addTheResult(historyEntity: HistoryEntity) = dao.insert(historyEntity)
    suspend fun deleteAllResults() = dao.deleteAllResults()
    fun getAllResults(): Flow<List<HistoryEntity>> =
        dao.getResults().flowOn(Dispatchers.IO).conflate()
}