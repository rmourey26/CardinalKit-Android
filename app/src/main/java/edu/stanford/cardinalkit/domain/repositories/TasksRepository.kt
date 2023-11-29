package io.quantumone.cardinalkit.domain.repositories

import io.quantumone.cardinalkit.domain.models.Response
import io.quantumone.cardinalkit.domain.models.tasks.CKTask
import kotlinx.coroutines.flow.Flow

interface TasksRepository {
    fun getTasks(): Flow<Response<List<CKTask>>>
}
