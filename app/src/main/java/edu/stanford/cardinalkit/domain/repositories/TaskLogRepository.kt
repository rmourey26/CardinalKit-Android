package io.quantumone.cardinalkit.domain.repositories

import io.quantumone.cardinalkit.domain.models.Response
import io.quantumone.cardinalkit.domain.models.tasks.CKTaskLog
import kotlinx.coroutines.flow.Flow

interface TaskLogRepository {
    fun getTaskLogs(): Flow<Response<List<CKTaskLog>>>
    suspend fun uploadTaskLog(log: CKTaskLog): Flow<Response<Void?>>
}
