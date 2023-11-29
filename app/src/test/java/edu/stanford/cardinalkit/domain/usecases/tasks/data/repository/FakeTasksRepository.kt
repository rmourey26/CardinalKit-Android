package io.quantumone.cardinalkit.domain.usecases.tasks.data.repository

import io.quantumone.cardinalkit.domain.models.Response
import io.quantumone.cardinalkit.domain.models.tasks.CKTask
import io.quantumone.cardinalkit.domain.repositories.TasksRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeTasksRepository : TasksRepository {
    private val tasks = mutableListOf<CKTask>()

    override fun getTasks(): Flow<Response<List<CKTask>>> {
        return flow {
            emit(Response.Success(tasks))
        }
    }

    fun insertTask(task: CKTask) {
        tasks.add(task)
    }
}
